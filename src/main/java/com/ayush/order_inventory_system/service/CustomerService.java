package com.ayush.order_inventory_system.service;

import com.ayush.order_inventory_system.model.Customer;
import com.ayush.order_inventory_system.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers");
        return repository.getAllCustomers();
    }

    public void addCustomer(Customer customer) {
        logger.info("Adding customer '{}'", customer.getCustomerName());

        repository.addCustomer(customer);

        logger.info("Customer '{}' added successfully",
                customer.getCustomerName());
    }

    public void updateCustomer(int id, Customer customer) {
        logger.info("Updating customer with id {}", id);

        repository.updateCustomer(id, customer);

        logger.info("Customer with id {} updated successfully", id);
    }

    public void deleteCustomer(int id) {
        logger.info("Deleting customer with id {}", id);

        repository.deleteCustomer(id);

        logger.info("Customer with id {} deleted successfully", id);
    }
}