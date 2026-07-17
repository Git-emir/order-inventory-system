package com.ayush.order_inventory_system.repository;

import com.ayush.order_inventory_system.exception.InsufficientStockException;
import com.ayush.order_inventory_system.model.Order;
import com.ayush.order_inventory_system.model.OrderRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Order(
                rs.getInt("Order_id"),
                rs.getInt("Customer_id"),
                rs.getTimestamp("order_date").toLocalDateTime(),
                rs.getDouble("Order_amount")
        ));
    }

    public void placeOrder(OrderRequest orderRequest) {

        // Step 1 - fetch price
        String productSql = "SELECT product_price FROM products WHERE product_id = ?";
        double price = jdbcTemplate.queryForObject(productSql, Double.class, orderRequest.getProductId());

        double orderAmount = price * orderRequest.getQuantity();

        // Step 2 - atomic check-and-update stock FIRST (fail fast before writing orders)
        String stockSql = "UPDATE products SET product_quantity = product_quantity - ? " +
                "WHERE product_id = ? AND product_quantity >= ?";

        int rowsAffected = jdbcTemplate.update(stockSql,
                orderRequest.getQuantity(),
                orderRequest.getProductId(),
                orderRequest.getQuantity());

        if (rowsAffected == 0) {
            throw new InsufficientStockException("Insufficient stock or product not found.");
        }

        // Step 3 - insert into orders, capture generated order_id
        String orderSql = "INSERT INTO orders (customer_id, order_date, order_amount) VALUES (?, NOW(), ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, orderRequest.getCustomerId());
            ps.setDouble(2, orderAmount);
            return ps;
        }, keyHolder);

        int generatedOrderId = keyHolder.getKey().intValue();

        // Step 4 - insert into order_items
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(orderItemSql, generatedOrderId, orderRequest.getProductId(), orderRequest.getQuantity(), price);
    }
}


