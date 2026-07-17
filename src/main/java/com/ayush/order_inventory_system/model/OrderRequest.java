package com.ayush.order_inventory_system.model;

import jakarta.validation.constraints.*;

public class OrderRequest {
    @Positive(message = "Customer ID must be valid")
    private int customerId;

    @Positive(message = "Product ID must be valid")
    private int productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;


        // constructor
        public OrderRequest(int customerId,int productId,int quantity) {
            this.customerId = customerId;
            this.productId = productId;
            this.quantity = quantity;
        }
        public int getCustomerId() { return customerId; }
        public int getProductId() { return productId; }
        public int getQuantity() { return quantity; }


}
