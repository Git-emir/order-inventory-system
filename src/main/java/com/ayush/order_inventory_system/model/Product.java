package com.ayush.order_inventory_system.model;

import jakarta.validation.constraints.*;

public class Product {
    private int productId;

    @NotBlank(message = "Product name is required")
    private String productName;

    @Positive(message = "Price must be greater than 0")
    private double productPrice;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int productQuantity;

    public Product(int productId, String productName, double productPrice, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getProductPrice() { return productPrice; }
    public int getProductQuantity() { return productQuantity; }
}