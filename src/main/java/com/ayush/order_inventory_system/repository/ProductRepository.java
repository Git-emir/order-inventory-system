package com.ayush.order_inventory_system.repository;

import com.ayush.order_inventory_system.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getDouble("product_price"),
                rs.getInt("product_quantity")
        ));
    }

    public void addProduct(Product product) {

        String sql = "INSERT INTO products (product_name, product_price, product_quantity) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                sql,
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity()
        );
    }
    public void updateProduct(int id,Product product) {

        String sql = "UPDATE products SET product_name=?, product_price=?, product_quantity=? WHERE product_id=?";

        jdbcTemplate.update(
                sql,
                product.getProductName(),
                product.getProductPrice(),
                product.getProductQuantity()
        );
    }

    public void deleteProduct(int id) {

        String sql = "DELETE FROM products WHERE product_id=?";

        jdbcTemplate.update(
                sql,id);
    }
}