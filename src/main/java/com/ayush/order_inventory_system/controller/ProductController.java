package com.ayush.order_inventory_system.controller;

import com.ayush.order_inventory_system.model.Product;
import com.ayush.order_inventory_system.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping
    public String addProduct(@Valid @RequestBody Product product) {
        service.addProduct(product);
        return "Product added successfully";
    }
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id,
                                @RequestBody Product product) {

        service.updateProduct(id, product);

        return "Product updated successfully";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {

        service.deleteProduct(id);

        return "Product deleted successfully";
    }
}