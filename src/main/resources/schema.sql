CREATE DATABASE IF NOT EXISTS order_inventory_db;
USE order_inventory_db;

CREATE TABLE IF NOT EXISTS products (
                                        product_id INT PRIMARY KEY AUTO_INCREMENT,
                                        product_name VARCHAR(100) NOT NULL,
    product_price DECIMAL(10,2) NOT NULL,
    product_quantity INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS customers (
                                         customer_id INT AUTO_INCREMENT PRIMARY KEY,
                                         customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(15) NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders (
                                      order_id INT PRIMARY KEY AUTO_INCREMENT,
                                      customer_id INT NOT NULL,
                                      order_date DATE NOT NULL,
                                      order_amount DECIMAL(10,3) NOT NULL
    );

CREATE TABLE IF NOT EXISTS order_items (
                                           order_item_id INT PRIMARY KEY AUTO_INCREMENT,
                                           order_id INT NOT NULL,
                                           product_id INT NOT NULL,
                                           quantity INT NOT NULL,
                                           price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(product_id)
    );