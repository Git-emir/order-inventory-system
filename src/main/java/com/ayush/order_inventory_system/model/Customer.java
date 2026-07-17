package com.ayush.order_inventory_system.model;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    // Constructor
    public Customer(int customerId, String customerName,
                   String customerEmail, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail= customerEmail;
        this.customerPhone = customerPhone;
    }

    // Getters
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getCustomerPhone() { return customerPhone; }
}