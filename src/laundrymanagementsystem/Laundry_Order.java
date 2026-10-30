/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package laundrymanagementsystem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Laundry_Order extends javax.swing.JFrame {
    int currentIndex = 0;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Laundry_Order.class.getName());

    /**
     * Creates new form LaundryOrder
     */
    public Laundry_Order() {
        initComponents();
        loadCustomers();
        loadDeliveries();
        loadEmployees();
        loadOrderStatuses();
        loadPayments();
        setTitle("Laundry order");
        setLocationRelativeTo(null);
    }
    private void loadCustomers() {
    cmbcustomer.removeAllItems();
    cmbcustomer.addItem("Select Customer");

    String sql = "SELECT customer_id, f_name, l_name FROM customer";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("customer_id");
            String firstName = rs.getString("f_name");
            String lastName = rs.getString("l_name");

            cmbcustomer.addItem(id + " - " + firstName + " " + lastName);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading customers: " + e.getMessage());
    }
}
    private void loadDeliveries() {
    cmbdelivery.removeAllItems();
    cmbdelivery.addItem("Select Delivery");

    String sql = "SELECT delivery_id FROM delivery";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("delivery_id");
            cmbdelivery.addItem(String.valueOf(id));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading deliveries: " + e.getMessage());
    }
}
    private void loadEmployees() {
    cmbemployee.removeAllItems();
    cmbemployee.addItem("Select Employee");

    String sql = "SELECT employee_id, f_name, l_name FROM employee";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("employee_id");
            String firstName = rs.getString("f_name");
            String lastName = rs.getString("l_name");

            cmbemployee.addItem(id + " - " + firstName + " " + lastName);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading employees: " + e.getMessage());
    }
}   private void loadOrderStatuses() {
    cmborder_status.removeAllItems();
    cmborder_status.addItem("Select Order Status");

    String sql = "SELECT status_id, status_name FROM order_status";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("status_id");
            String statusName = rs.getString("status_name");

            cmborder_status.addItem(id + " - " + statusName);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading order statuses: " + e.getMessage());
    }
}
    private void loadPayments() {
    cmbpayment.removeAllItems();
    cmbpayment.addItem("Select Payment");

    String sql = "SELECT payment_id FROM payment";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("payment_id");

            cmbpayment.addItem(String.valueOf(id));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error loading payments: " + e.getMessage());
    }
    
}
    
private void displayOrder(int index) {
    String sql = "SELECT * FROM laundry_order ORDER BY order_id";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        int count = 0;

        while (rs.next()) {
            if (count == index) {

                dateOrder.setDate(rs.getDate("order_date"));
                dateExpected.setDate(rs.getDate("expected_date"));

                cmbdelivery.setSelectedItem(
                        String.valueOf(rs.getInt("DELIVERY_delivery_id")));

                selectComboById(cmbcustomer,
                        rs.getInt("CUSTOMER_customer_id"));

                selectComboById(cmborder_status,
                        rs.getInt("ORDER_STATUS_status_id"));

                selectComboById(cmbemployee,
                        rs.getInt("EMPLOYEE_employee_id"));

                cmbpayment.setSelectedItem(
                        String.valueOf(rs.getInt("PAYMENT_payment_id")));

                return;
            }

            count++;
        }

        JOptionPane.showMessageDialog(this,
                "No order found at this position.");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error displaying order: " + e.getMessage());
    }
}
    
private void selectComboById(javax.swing.JComboBox<String> combo, int id) {
    for (int i = 1; i < combo.getItemCount(); i++) {
        String item = combo.getItemAt(i);

        if (item.equals(String.valueOf(id))
                || item.startsWith(id + " - ")) {
            combo.setSelectedIndex(i);
            return;
        }
    }

    combo.setSelectedIndex(0);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnupdate = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btnfind = new javax.swing.JButton();
        btnfirst = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnprevious = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        lblorder_state = new javax.swing.JLabel();
        lblexpected_date = new javax.swing.JLabel();
        lbldelivery = new javax.swing.JLabel();
        lblcustomer = new javax.swing.JLabel();
        lblorder_status = new javax.swing.JLabel();
        lblemployee = new javax.swing.JLabel();
        lblpayment = new javax.swing.JLabel();
        cmbdelivery = new javax.swing.JComboBox<>();
        cmbcustomer = new javax.swing.JComboBox<>();
        cmborder_status = new javax.swing.JComboBox<>();
        cmbemployee = new javax.swing.JComboBox<>();
        cmbpayment = new javax.swing.JComboBox<>();
        btnclear = new javax.swing.JButton();
        dateOrder = new com.toedter.calendar.JDateChooser();
        dateExpected = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnupdate.setText("Update");
        btnupdate.addActionListener(this::btnupdateActionPerformed);

        btnsave.setText("Save");
        btnsave.addActionListener(this::btnsaveActionPerformed);

        btnfind.setText("Find");
        btnfind.addActionListener(this::btnfindActionPerformed);

        btnfirst.setText("First");
        btnfirst.addActionListener(this::btnfirstActionPerformed);

        btnnext.setText("Next");
        btnnext.addActionListener(this::btnnextActionPerformed);

        btnprevious.setText("Previous");
        btnprevious.addActionListener(this::btnpreviousActionPerformed);

        btnlast.setText("Last");
        btnlast.addActionListener(this::btnlastActionPerformed);

        btnexit.setText("Exit");
        btnexit.addActionListener(this::btnexitActionPerformed);

        lblorder_state.setText("Order_date");

        lblexpected_date.setText("Expected_date");

        lbldelivery.setText("Delivery");

        lblcustomer.setText("Customer");

        lblorder_status.setText("Order status");

        lblemployee.setText("Employee");

        lblpayment.setText("Payment");

        cmbdelivery.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select delivery" }));

        cmbcustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select customer" }));

        cmborder_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select order_status" }));

        cmbemployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select employee" }));

        cmbpayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select payment" }));

        btnclear.setText("Clear");
        btnclear.addActionListener(this::btnclearActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnfind)
                        .addGap(48, 48, 48)
                        .addComponent(btnfirst)
                        .addGap(70, 70, 70)
                        .addComponent(btnnext)
                        .addGap(65, 65, 65)
                        .addComponent(btnprevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(btnlast))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblorder_state)
                                    .addComponent(lblexpected_date)
                                    .addComponent(lblemployee)
                                    .addComponent(lblpayment)
                                    .addComponent(lblorder_status)
                                    .addComponent(lblcustomer)
                                    .addComponent(lbldelivery))
                                .addGap(94, 94, 94)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbdelivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbcustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmborder_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbemployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbpayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateExpected, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                    .addComponent(dateOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnupdate)
                                .addGap(163, 163, 163)
                                .addComponent(btnsave)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnclear)))
                .addGap(84, 84, 84))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblorder_state)
                    .addComponent(dateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblexpected_date)
                    .addComponent(dateExpected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbldelivery)
                    .addComponent(cmbdelivery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblcustomer)
                    .addComponent(cmbcustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblorder_status)
                    .addComponent(cmborder_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblemployee)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbemployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblpayment)
                    .addComponent(cmbpayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdate)
                    .addComponent(btnsave)
                    .addComponent(btnclear))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnfind)
                    .addComponent(btnfirst)
                    .addComponent(btnnext)
                    .addComponent(btnprevious)
                    .addComponent(btnlast))
                .addGap(34, 34, 34)
                .addComponent(btnexit)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
                                        

    // Get selected dates from the calendars
    java.util.Date selectedOrderDate = dateOrder.getDate();
    java.util.Date selectedExpectedDate = dateExpected.getDate();

    // Check dates
    if (selectedOrderDate == null || selectedExpectedDate == null) {
        JOptionPane.showMessageDialog(this,
                "Please select both the order date and expected date.");
        return;
    }

    // Convert selected dates to SQL dates
    java.sql.Date orderDate =
            new java.sql.Date(selectedOrderDate.getTime());

    java.sql.Date expectedDate =
            new java.sql.Date(selectedExpectedDate.getTime());

    // Check that expected date is not before order date
    if (expectedDate.before(orderDate)) {
        JOptionPane.showMessageDialog(this,
                "Expected date cannot be before the order date.");
        return;
    }

    // Check combo boxes
    if (cmbcustomer.getSelectedIndex() == 0 ||
        cmbdelivery.getSelectedIndex() == 0 ||
        cmbemployee.getSelectedIndex() == 0 ||
        cmborder_status.getSelectedIndex() == 0 ||
        cmbpayment.getSelectedIndex() == 0) {

        JOptionPane.showMessageDialog(this,
                "Please select all required options.");
        return;
    }

    try {
        // Extract customer ID from "1 - John Doe"
        String customerValue =
                cmbcustomer.getSelectedItem().toString();
        int customerId =
                Integer.parseInt(customerValue.split(" - ")[0]);

        // Extract delivery ID
        int deliveryId = Integer.parseInt(
                cmbdelivery.getSelectedItem().toString());

        // Extract employee ID from "1 - John Doe"
        String employeeValue =
                cmbemployee.getSelectedItem().toString();
        int employeeId =
                Integer.parseInt(employeeValue.split(" - ")[0]);

        // Extract order status ID from "1 - Received"
        String statusValue =
                cmborder_status.getSelectedItem().toString();
        int statusId =
                Integer.parseInt(statusValue.split(" - ")[0]);

        // Extract payment ID
        int paymentId = Integer.parseInt(
                cmbpayment.getSelectedItem().toString());

        // SQL INSERT statement
        String sql = "INSERT INTO laundry_order "
                + "(order_date, expected_date, DELIVERY_delivery_id, "
                + "CUSTOMER_customer_id, ORDER_STATUS_status_id, "
                + "EMPLOYEE_employee_id, PAYMENT_payment_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Set values
            pst.setDate(1, orderDate);
            pst.setDate(2, expectedDate);
            pst.setInt(3, deliveryId);
            pst.setInt(4, customerId);
            pst.setInt(5, statusId);
            pst.setInt(6, employeeId);
            pst.setInt(7, paymentId);

            // Execute insert
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Laundry order saved successfully!");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error saving laundry order: " + e.getMessage());
    }



 
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnfindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfindActionPerformed
        

    String input = JOptionPane.showInputDialog(
            this, "Enter Order ID to find:");

    if (input == null || input.trim().isEmpty()) {
        return;
    }

    try {
        int orderId = Integer.parseInt(input.trim());

        String sql = "SELECT * FROM laundry_order WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, orderId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    dateOrder.setDate(rs.getDate("order_date"));
                    dateExpected.setDate(rs.getDate("expected_date"));

                    cmbdelivery.setSelectedItem(
                            String.valueOf(rs.getInt("DELIVERY_delivery_id")));

                    selectComboById(cmbcustomer,
                            rs.getInt("CUSTOMER_customer_id"));

                    selectComboById(cmborder_status,
                            rs.getInt("ORDER_STATUS_status_id"));

                    selectComboById(cmbemployee,
                            rs.getInt("EMPLOYEE_employee_id"));

                    cmbpayment.setSelectedItem(
                            String.valueOf(rs.getInt("PAYMENT_payment_id")));

                    JOptionPane.showMessageDialog(
                            this, "Order found successfully!");
                } else {
                    JOptionPane.showMessageDialog(
                            this, "No order found with ID: " + orderId);
                }
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
                this, "Please enter a valid numeric Order ID.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Error finding order: " + e.getMessage());
    }

                               
    
 
    }//GEN-LAST:event_btnfindActionPerformed

    private void btnfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfirstActionPerformed
      currentIndex = 0;
      displayOrder(currentIndex);                                   
    
    }//GEN-LAST:event_btnfirstActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
                                        
    currentIndex++;
    displayOrder(currentIndex);

    }//GEN-LAST:event_btnnextActionPerformed

    private void btnpreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpreviousActionPerformed
          if (currentIndex > 0) {
        currentIndex--;
        displayOrder(currentIndex);
          }   
          else {
        JOptionPane.showMessageDialog(this, "This is the first order.");
    }                                   
    

    }//GEN-LAST:event_btnpreviousActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
                                        
    try {
        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM laundry_order";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int total = rs.getInt(1);

            if (total > 0) {
                currentIndex = total - 1;
                displayOrder(currentIndex);
            }
        }

        rs.close();
        pst.close();
        con.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    }//GEN-LAST:event_btnlastActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
                                         
    int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit?",
            "Exit",
            JOptionPane.YES_NO_OPTION
    );

    if (choice == JOptionPane.YES_OPTION) {
        this.dispose();
    }

    }//GEN-LAST:event_btnexitActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
         
    String input = JOptionPane.showInputDialog(
            this, "Enter the Order ID to update:");

    if (input == null || input.trim().isEmpty()) {
        return;
    }

    try {
        int orderId = Integer.parseInt(input.trim());

        java.util.Date selectedOrderDate = dateOrder.getDate();
        java.util.Date selectedExpectedDate = dateExpected.getDate();

        if (selectedOrderDate == null || selectedExpectedDate == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select both the order date and expected date.");
            return;
        }

        java.sql.Date orderDate =
                new java.sql.Date(selectedOrderDate.getTime());
        java.sql.Date expectedDate =
                new java.sql.Date(selectedExpectedDate.getTime());

        if (expectedDate.before(orderDate)) {
            JOptionPane.showMessageDialog(this,
                    "Expected date cannot be before the order date.");
            return;
        }

        if (cmbcustomer.getSelectedIndex() == 0
                || cmbdelivery.getSelectedIndex() == 0
                || cmbemployee.getSelectedIndex() == 0
                || cmborder_status.getSelectedIndex() == 0
                || cmbpayment.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select all required options.");
            return;
        }

        int customerId = Integer.parseInt(
                cmbcustomer.getSelectedItem().toString().split(" - ")[0]);

        int deliveryId = Integer.parseInt(
                cmbdelivery.getSelectedItem().toString());

        int employeeId = Integer.parseInt(
                cmbemployee.getSelectedItem().toString().split(" - ")[0]);

        int statusId = Integer.parseInt(
                cmborder_status.getSelectedItem().toString().split(" - ")[0]);

        int paymentId = Integer.parseInt(
                cmbpayment.getSelectedItem().toString());

        String sql = "UPDATE laundry_order SET "
                + "order_date = ?, expected_date = ?, "
                + "DELIVERY_delivery_id = ?, CUSTOMER_customer_id = ?, "
                + "ORDER_STATUS_status_id = ?, EMPLOYEE_employee_id = ?, "
                + "PAYMENT_payment_id = ? WHERE order_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDate(1, orderDate);
            pst.setDate(2, expectedDate);
            pst.setInt(3, deliveryId);
            pst.setInt(4, customerId);
            pst.setInt(5, statusId);
            pst.setInt(6, employeeId);
            pst.setInt(7, paymentId);
            pst.setInt(8, orderId);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this,
                        "Laundry order updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "No order found with ID: " + orderId);
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                "Please enter a valid numeric Order ID.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Error updating laundry order: " + e.getMessage());
    }


           
   

    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        

    // Clear the date choosers
    dateOrder.setDate(null);
    dateExpected.setDate(null);

    // Reset combo boxes to their first item
    cmbcustomer.setSelectedIndex(0);
    cmbdelivery.setSelectedIndex(0);
    cmbemployee.setSelectedIndex(0);
    cmborder_status.setSelectedIndex(0);
    cmbpayment.setSelectedIndex(0);

                                 
    

    }//GEN-LAST:event_btnclearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Laundry_Order().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnfind;
    private javax.swing.JButton btnfirst;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnext;
    private javax.swing.JButton btnprevious;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cmbcustomer;
    private javax.swing.JComboBox<String> cmbdelivery;
    private javax.swing.JComboBox<String> cmbemployee;
    private javax.swing.JComboBox<String> cmborder_status;
    private javax.swing.JComboBox<String> cmbpayment;
    private com.toedter.calendar.JDateChooser dateExpected;
    private com.toedter.calendar.JDateChooser dateOrder;
    private javax.swing.JLabel lblcustomer;
    private javax.swing.JLabel lbldelivery;
    private javax.swing.JLabel lblemployee;
    private javax.swing.JLabel lblexpected_date;
    private javax.swing.JLabel lblorder_state;
    private javax.swing.JLabel lblorder_status;
    private javax.swing.JLabel lblpayment;
    // End of variables declaration//GEN-END:variables
}
