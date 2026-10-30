/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laundrymanagementsystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    // Customer operations supported by the server.
    public static final String SAVE = "SAVE";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String FIND = "FIND";
    public static final String FIRST = "FIRST";
    public static final String NEXT = "NEXT";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String LAST = "LAST";
    public static final String GET_ALL = "GET_ALL";

    // Request information.
    private String action;
    private int customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    // Response information.
    private boolean success;
    private String message;
    private CustomerData customer;
    private List<CustomerData> customers = new ArrayList<>();

    public CustomerRequest() {
    }

    // Getters and setters for the request and response fields.

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }

    public List<CustomerData> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerData> customers) {
        this.customers = customers;
    }

    /**
     * Represents one customer record returned by the server.
     */
    public static class CustomerData implements Serializable {

        private static final long serialVersionUID = 1L;

        private int customerId;
        private String firstName;
        private String lastName;
        private String phoneNumber;

        public CustomerData(
                int customerId,
                String firstName,
                String lastName,
                String phoneNumber) {

            this.customerId = customerId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }
}