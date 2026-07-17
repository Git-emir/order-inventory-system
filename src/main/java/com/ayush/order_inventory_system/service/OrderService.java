package com.ayush.order_inventory_system.service;

import com.ayush.order_inventory_system.model.Order;
import com.ayush.order_inventory_system.model.OrderRequest;
import com.ayush.order_inventory_system.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return repository.getAllOrders();
    }

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {

        logger.info(
                "Placing order: customerId={}, productId={}, quantity={}",
                orderRequest.getCustomerId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );

        repository.placeOrder(orderRequest);

        logger.info(
                "Order placed successfully for customerId={}",
                orderRequest.getCustomerId()
        );
    }
}