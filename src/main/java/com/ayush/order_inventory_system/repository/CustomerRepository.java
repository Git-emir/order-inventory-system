package com.ayush.order_inventory_system.repository;

import com.ayush.order_inventory_system.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(
                rs.getInt("customer_id"),
                rs.getString("customer_name"),
                rs.getString("customer_email"),
                rs.getString("customer_phone")
        ));
    }

    public void addCustomer(Customer customer) {

        String sql = "INSERT INTO customers (customer_name, customer_email, customer_phone) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                customer.getCustomerPhone()
        );
    }
    public void updateCustomer(int id,Customer customer) {

        String sql = "UPDATE customers SET customer_name=?, customer_email=?, customer_phone=? WHERE customer_id=?";

        jdbcTemplate.update(
                sql,
                customer.getCustomerName(),
                customer.getCustomerEmail(),
                customer.getCustomerPhone(),
                id
        );
    }

    public void deleteCustomer(int id) {

        String sql = "DELETE FROM customers WHERE customer_id=?";

        jdbcTemplate.update(
                sql,id);
    }
}