package com.ayush.order_inventory_system.controller;

import com.ayush.order_inventory_system.model.Customer;
import com.ayush.order_inventory_system.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {

        service.addCustomer(customer);

        return "Customer added successfully";
    }
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable int id,
                                @RequestBody Customer customer) {

        service.updateCustomer(id, customer);

        return "Customer updated successfully";
    }
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {

        service.deleteCustomer(id);

        return "Customer deleted successfully";
    }
}