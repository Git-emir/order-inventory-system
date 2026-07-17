package com.ayush.order_inventory_system.model;
import java.time.LocalDateTime;



public class Order{

    private int orderId;
    private int customerId;
    private LocalDateTime orderDate;
    private double orderAmount;

    // Constructor
    public Order(int orderId,
                 int customerId,
                 LocalDateTime orderDate,
                 double orderAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getOrderAmount() { return orderAmount; }
}