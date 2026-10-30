
package laundrymanagementsystem;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaundryServer {

    // The port must match the port used by LaundryClient.
    private static final int PORT = 50000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Laundry Server started.");
            System.out.println("Waiting for client connections on port " + PORT);

            // Keep the server running so it can accept multiple clients.
            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "Client connected: " + clientSocket.getInetAddress());

                // Each client gets its own communication thread.
                Thread clientThread = new Thread(
                        () -> handleClient(clientSocket));

                clientThread.start();
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    /**
     * Receives requests from one connected client and sends responses.
     */
    private static void handleClient(Socket clientSocket) {

        try (
            Socket socket = clientSocket;

            // The client creates its ObjectOutputStream first.
            ObjectInputStream input =
                    new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream output =
                    new ObjectOutputStream(socket.getOutputStream())
        ) {
            output.flush();

            while (true) {
                Object receivedObject = input.readObject();

                if (!(receivedObject instanceof CustomerRequest)) {
                    CustomerRequest error = new CustomerRequest();
                    error.setSuccess(false);
                    error.setMessage("Invalid request received.");

                    output.writeObject(error);
                    output.flush();
                    output.reset();
                    continue;
                }

                CustomerRequest request =
                        (CustomerRequest) receivedObject;

                // Process the request using the existing MySQL database.
                CustomerRequest response = processRequest(request);

                output.writeObject(response);
                output.flush();

                // Prevent old serialized objects from being reused.
                output.reset();
            }

        } catch (EOFException e) {
            System.out.println("Client disconnected.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Client communication error: " + e.getMessage());
        }
    }

    /**
     * Performs the requested customer operation.
     */
    private static CustomerRequest processRequest(CustomerRequest request) {

        CustomerRequest response = new CustomerRequest();

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                throw new SQLException(
                        "Could not connect to the laundry database.");
            }

            String action = request.getAction();

            if (action == null) {
                throw new SQLException("No customer action was specified.");
            }

            switch (action) {

                case CustomerRequest.SAVE:
                    saveCustomer(con, request, response);
                    break;

                case CustomerRequest.UPDATE:
                    updateCustomer(con, request, response);
                    break;

                case CustomerRequest.DELETE:
                    deleteCustomer(con, request, response);
                    break;

                case CustomerRequest.FIND:
                    findCustomer(con, request, response);
                    break;

                case CustomerRequest.FIRST:
                    findFirstOrLast(con, response, true);
                    break;

                case CustomerRequest.LAST:
                    findFirstOrLast(con, response, false);
                    break;

                case CustomerRequest.NEXT:
                    findAdjacentCustomer(con, request, response, true);
                    break;

                case CustomerRequest.PREVIOUS:
                    findAdjacentCustomer(con, request, response, false);
                    break;

                case CustomerRequest.GET_ALL:
                    getAllCustomers(con, response);
                    break;

                default:
                    response.setSuccess(false);
                    response.setMessage("Unknown customer operation.");
                    break;
            }

        } catch (SQLException e) {
            response.setSuccess(false);
            response.setMessage("Database error: " + e.getMessage());

            System.out.println(response.getMessage());
        }

        return response;
    }

    private static void saveCustomer(
            Connection con,
            CustomerRequest request,
            CustomerRequest response) throws SQLException {

        String sql = "INSERT INTO customer "
                + "(f_name, l_name, phone_number) VALUES (?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, request.getFirstName());
            pst.setString(2, request.getLastName());
            pst.setString(3, request.getPhoneNumber());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                int customerId = 0;

                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        customerId = keys.getInt(1);
                    }
                }

                response.setSuccess(true);
                response.setMessage("Customer saved successfully.");

                response.setCustomer(new CustomerRequest.CustomerData(
                        customerId,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPhoneNumber()));
            } else {
                response.setSuccess(false);
                response.setMessage("Customer was not saved.");
            }
        }
    }

    private static void updateCustomer(
            Connection con,
            CustomerRequest request,
            CustomerRequest response) throws SQLException {

        String sql = "UPDATE customer SET f_name = ?, l_name = ?, "
                + "phone_number = ? WHERE customer_id = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, request.getFirstName());
            pst.setString(2, request.getLastName());
            pst.setString(3, request.getPhoneNumber());
            pst.setInt(4, request.getCustomerId());

            int rows = pst.executeUpdate();

            response.setSuccess(rows > 0);
            response.setMessage(rows > 0
                    ? "Customer updated successfully."
                    : "Customer was not found or no changes were made.");
        }
    }

    private static void deleteCustomer(
            Connection con,
            CustomerRequest request,
            CustomerRequest response) throws SQLException {

        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, request.getCustomerId());

            int rows = pst.executeUpdate();

            response.setSuccess(rows > 0);
            response.setMessage(rows > 0
                    ? "Customer deleted successfully."
                    : "Customer was not found.");
        }
    }

    private static void findCustomer(
            Connection con,
            CustomerRequest request,
            CustomerRequest response) throws SQLException {

        String sql = "SELECT customer_id, f_name, l_name, phone_number "
                + "FROM customer WHERE f_name = ? "
                + "ORDER BY customer_id ASC LIMIT 1";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, request.getFirstName());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    response.setSuccess(true);
                    response.setMessage("Customer found.");
                    response.setCustomer(readCustomer(rs));
                } else {
                    response.setSuccess(false);
                    response.setMessage("Customer not found.");
                }
            }
        }
    }

    private static void findFirstOrLast(
            Connection con,
            CustomerRequest response,
            boolean first) throws SQLException {

        String direction = first ? "ASC" : "DESC";

        String sql = "SELECT customer_id, f_name, l_name, phone_number "
                + "FROM customer ORDER BY customer_id "
                + direction + " LIMIT 1";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                response.setSuccess(true);
                response.setMessage("Customer record found.");
                response.setCustomer(readCustomer(rs));
            } else {
                response.setSuccess(false);
                response.setMessage("No customers found.");
            }
        }
    }

    private static void findAdjacentCustomer(
            Connection con,
            CustomerRequest request,
            CustomerRequest response,
            boolean next) throws SQLException {

        String comparison = next ? ">" : "<";
        String direction = next ? "ASC" : "DESC";

        String sql = "SELECT customer_id, f_name, l_name, phone_number "
                + "FROM customer WHERE customer_id " + comparison + " ? "
                + "ORDER BY customer_id " + direction + " LIMIT 1";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, request.getCustomerId());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    response.setSuccess(true);
                    response.setMessage("Customer record found.");
                    response.setCustomer(readCustomer(rs));
                } else {
                    response.setSuccess(false);
                    response.setMessage(next
                            ? "You are already at the last customer."
                            : "You are already at the first customer.");
                }
            }
        }
    }

    private static void getAllCustomers(
            Connection con,
            CustomerRequest response) throws SQLException {

        List<CustomerRequest.CustomerData> customers =
                new ArrayList<>();

        String sql = "SELECT customer_id, f_name, l_name, phone_number "
                + "FROM customer ORDER BY customer_id ASC";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                customers.add(readCustomer(rs));
            }
        }

        response.setSuccess(true);
        response.setMessage("Customer records retrieved.");
        response.setCustomers(customers);
    }

    /**
     * Converts a database row into a serializable customer record.
     */
    private static CustomerRequest.CustomerData readCustomer(
            ResultSet rs) throws SQLException {

        return new CustomerRequest.CustomerData(
                rs.getInt("customer_id"),
                rs.getString("f_name"),
                rs.getString("l_name"),
                rs.getString("phone_number"));
    }
}
