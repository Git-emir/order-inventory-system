package com.ayush.order_inventory_system.controller;

import com.ayush.order_inventory_system.model.Order;
import com.ayush.order_inventory_system.model.OrderRequest;
import com.ayush.order_inventory_system.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @PostMapping
    public String placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        service.placeOrder(orderRequest);
        return "Order Placed successfully";
    }
}