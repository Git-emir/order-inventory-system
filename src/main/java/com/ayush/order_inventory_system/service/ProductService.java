package com.ayush.order_inventory_system.service;

import com.ayush.order_inventory_system.model.Product;
import com.ayush.order_inventory_system.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return repository.getAllProducts();
    }

    public void addProduct(Product product) {
        logger.info("Adding product '{}'", product.getProductName());

        repository.addProduct(product);

        logger.info("Product '{}' added successfully",
                product.getProductName());
    }

    public void updateProduct(int id, Product product) {
        logger.info("Updating product with id {}", id);

        repository.updateProduct(id, product);

        logger.info("Product with id {} updated successfully", id);
    }

    public void deleteProduct(int id) {
        logger.info("Deleting product with id {}", id);

        repository.deleteProduct(id);

        logger.info("Product with id {} deleted successfully", id);
    }
}