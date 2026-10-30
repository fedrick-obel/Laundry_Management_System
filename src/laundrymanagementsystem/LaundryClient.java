
package laundrymanagementsystem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LaundryClient {

    // The address and port where the server is running.
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 50000;

    // One shared connection for the Customer form.
    private static Socket socket;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;

    private LaundryClient() {
        // Prevent other classes from creating separate clients.
    }

    /**
     * Connects to the server if a connection does not already exist.
     */
    private static void connect() throws IOException {

        if (socket != null
                && socket.isConnected()
                && !socket.isClosed()) {
            return;
        }

        closeConnection();

        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        // Create the output stream first to send its stream header.
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();

        // Wait for the server's object stream header.
        input = new ObjectInputStream(socket.getInputStream());

        System.out.println("Connected to Laundry Server.");
    }

    /**
     * Sends a request and waits for the server's response.
     */
    public static synchronized CustomerRequest sendRequest(
            CustomerRequest request)
            throws IOException, ClassNotFoundException {

        connect();

        try {
            // Send the request to the server.
            output.writeObject(request);
            output.flush();
            output.reset();

            // Receive the server's response.
            Object receivedObject = input.readObject();

            if (!(receivedObject instanceof CustomerRequest)) {
                throw new IOException(
                        "The server returned an invalid response.");
            }

            return (CustomerRequest) receivedObject;

        } catch (IOException | ClassNotFoundException e) {

            // Close a broken connection so a later request can reconnect.
            closeConnection();

            throw e;
        }
    }

    /**
     * Closes the connection when it is no longer needed.
     */
    public static synchronized void disconnect() {
        closeConnection();
    }

    private static void closeConnection() {

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing input stream.");
        }

        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing output stream.");
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing socket.");
        }

        input = null;
        output = null;
        socket = null;
    }
}
