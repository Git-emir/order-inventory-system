<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=6e40c9&height=160&section=header&text=Order%20%26%20Inventory%20Management%20System&fontSize=32&fontColor=ffffff&fontAlignY=45&animation=fadeIn" width="100%"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.16-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
  <img src="https://img.shields.io/badge/Status-Active-2E8B57?style=for-the-badge"/>
</p>

---

## Overview

A backend REST API for managing products, customers, and orders — built with Java, Spring Boot, and MySQL. Implements a clean 3-layer architecture (Controller → Service → Repository), transaction management, race-condition-safe inventory updates, and global exception handling.

---

## Architecture

```
┌─────────────────────────────────────────────────────┐
│                  Client (Postman)                     │
└───────────────────────┬─────────────────────────────┘
                         │ HTTP / JSON
                         ▼
┌─────────────────────────────────────────────────────┐
│           Controller Layer (@RestController)          │
│  ProductController · OrderController · CustomerController │
└───────────────────────┬─────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────┐
│             Service Layer (@Service)                  │
│    ProductService · OrderService · CustomerService    │
│           Business logic · @Transactional             │
└───────────────────────┬─────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────┐
│           Repository Layer (@Repository)              │
│  ProductRepository · OrderRepository · CustomerRepository │
│               JdbcTemplate · Raw SQL                  │
└───────────────────────┬─────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────┐
│                  MySQL Database                       │
│      products · customers · orders · order_items      │
└─────────────────────────────────────────────────────┘

     @ControllerAdvice — Global Exception Handler
     Catches all exceptions → clean JSON responses
```

---

## Database Schema

```sql
products        customers         orders              order_items
────────        ─────────         ──────              ───────────
product_id PK   customer_id PK    order_id PK         order_item_id PK
product_name    customer_name     customer_id FK   →  order_id FK
product_price   customer_email    order_date          product_id FK
product_qty     customer_phone    order_amount        quantity
                                                      price
```

---

## API Endpoints

### Products
| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| GET | `/products` | Get all products | — |
| POST | `/products` | Add a product | `{"productName": "Laptop", "productPrice": 75000, "productQuantity": 10}` |
| PUT | `/products/{id}` | Update product | `{"productName": "Laptop", "productPrice": 80000, "productQuantity": 10}` |
| DELETE | `/products/{id}` | Delete product | — |

### Orders
| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| GET | `/orders` | Get all orders | — |
| POST | `/orders` | Place an order | `{"customerId": 1, "productId": 1, "quantity": 2}` |

### Customers
| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| GET | `/customers` | Get all customers | — |
| POST | `/customers` | Add a customer | `{"customerName": "John", "customerEmail": "john@email.com", "customerPhone": "9999999999"}` |
| PUT | `/customers/{id}` | Update customer | `{"customerName": "John", "customerEmail": "john@email.com", "customerPhone": "9999999999"}` |
| DELETE | `/customers/{id}` | Delete customer | — |

---

## Order Placement Flow

```
POST /orders { customerId, productId, quantity }
        │
        ▼
1. Fetch product price from DB
        │
        ▼
2. Calculate orderAmount = price × quantity
        │
        ▼
3. Atomic stock check + deduction (single SQL statement)
   UPDATE products SET quantity = quantity - ?
   WHERE product_id = ? AND quantity >= ?
        │
        ├── 0 rows affected → InsufficientStockException → 400
        │
        ▼
4. INSERT into orders → capture generated order_id (KeyHolder)
        │
        ▼
5. INSERT into order_items using generated order_id
        │
        ▼
   @Transactional wraps steps 1–5
   Any failure → automatic rollback
```

---

## Technical Highlights

**Race-Condition-Safe Stock Management**

Stock deduction uses a single atomic SQL UPDATE instead of a read-then-write pattern. This prevents overselling under concurrent requests without explicit row locking.

```sql
UPDATE products
SET product_quantity = product_quantity - ?
WHERE product_id = ? AND product_quantity >= ?
```

If `rowsAffected == 0`, the order is rejected immediately with `InsufficientStockException`.

**Transaction Management**

`@Transactional` on `OrderService.placeOrder()` wraps all three operations — stock update, order insert, order_items insert — atomically. Any failure rolls back all changes automatically.

**Exception Handling**

| Exception | HTTP Status | Meaning |
|---|---|---|
| `InsufficientStockException` | 400 Bad Request | Business rule violation — client can fix |
| `MethodArgumentNotValidException` | 400 Validation Failed | Invalid request body |
| `Exception` (fallback) | 500 Internal Server Error | Unexpected server-side failure |

**Input Validation**

Request bodies are validated at the boundary using `@Valid` before reaching business logic. Negative prices, zero quantities, and blank names are rejected at the controller level.

**Security**

Database credentials are read from environment variables at runtime — never hardcoded in source code or configuration files.

---

## Running Locally

**Prerequisites:** Java 21, MySQL 8, Maven

```bash
# 1. Clone the repository
git clone https://github.com/Git-emir/order-inventory-system.git
cd order-inventory-system

# 2. Set up the database
mysql -u root -p < src/main/resources/schema.sql

# 3. Set the database password as an environment variable
export DB_PASSWORD=your_mysql_password

# 4. Run the application
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

---

## Project Structure

```
src/main/java/com/ayush/order_inventory_system/
├── controller/
│   ├── ProductController.java
│   ├── OrderController.java
│   └── CustomerController.java
├── service/
│   ├── ProductService.java
│   ├── OrderService.java
│   └── CustomerService.java
├── repository/
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   └── CustomerRepository.java
├── model/
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── OrderRequest.java
│   └── Customer.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── InsufficientStockException.java
└── OrderInventorySystemApplication.java

src/main/resources/
├── application.properties
└── schema.sql
```

---

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=6e40c9&height=100&section=footer" width="100%"/>
</p>