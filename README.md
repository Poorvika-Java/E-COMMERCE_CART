E-Commerce Cart & Checkout Module

Project Overview
The E-Commerce Cart & Checkout Module is a Spring Boot backend application that manages shopping cart operations, coupon application, inventory validation, checkout workflow, order creation, payment simulation, and order history retrieval.

The project follows a layered architecture using Controller, Service, Repository, DTO, Entity, and Exception Handling layers. It demonstrates real-world e-commerce backend concepts including transactional processing, inventory management, discount handling, and pagination.

Features
Cart Management
Add product to cart
Update cart item quantity
Partially update quantity using PATCH
Remove product from cart
View cart details
Calculate total cart value
Coupon Management
Apply percentage discount coupons
Apply flat discount coupons
Validate coupon expiry date
Validate active/inactive coupons
Checkout Management
Validate inventory before checkout
Create order from cart items
Simulate payment processing
Generate order summary
Clear cart after successful payment
Inventory Management
Reduce stock after successful checkout
Prevent checkout for insufficient inventory
Maintain product stock consistency
Order Management
Retrieve order by ID
Retrieve order history by user
Retrieve all orders with pagination
Exception Handling
Resource Not Found
Insufficient Stock
Empty Cart
Invalid Coupon
Expired Coupon
Global Exception Handling
Technology Stack
Java 21
Spring Boot 3
Spring Data JPA
Hibernate
MySQL
Lombok
Maven
Postman
JUnit 5
Mockito
Project Structure
src/main/java/com/ecommerce

├── Controller
│   ├── UserController
│   ├── ProductController
│   ├── CartController
│   ├── CouponController
│   ├── CheckoutController
│   └── OrderController
│
├── Service
│
├── ServiceImpl
│
├── Repository
│
├── Entity
│
├── RequestDto
│
├── ResponseDto
│
├── Exception
│
├── Enums
│
└── Config
Database Tables
Users
| Column | Type | | ------ | ------- | | id | BIGINT | | name | VARCHAR | | email | VARCHAR |

Products
| Column | Type | | -------------- | ------- | | id | BIGINT | | name | VARCHAR | | price | DECIMAL | | stock_quantity | INTEGER |

Carts
| Column | Type | | ------- | ------ | | id | BIGINT | | user_id | BIGINT |

Cart Items
| Column | Type | | ---------- | ------- | | id | BIGINT | | cart_id | BIGINT | | product_id | BIGINT | | quantity | INTEGER |

Orders
| Column | Type | | --------------- | -------- | | id | BIGINT | | user_id | BIGINT | | total_amount | DECIMAL | | discount_amount | DECIMAL | | final_amount | DECIMAL | | status | VARCHAR | | created_at | DATETIME |

Order Items
| Column | Type | | ---------- | ------- | | id | BIGINT | | order_id | BIGINT | | product_id | BIGINT | | quantity | INTEGER | | price | DECIMAL |

Coupons
| Column | Type | | -------------- | ------- | | id | BIGINT | | code | VARCHAR | | discount_type | VARCHAR | | discount_value | DECIMAL | | expiry_date | DATE | | active | BOOLEAN |

API Endpoints
User APIs
Create User
POST /api/users
Get All Users
GET /api/users
Get User By Id
GET /api/users/{id}
Product APIs
Create Product
POST /api/products
Get All Products
GET /api/products
Get Product By Id
GET /api/products/{id}
Cart APIs
Add Product To Cart
POST /api/cart/add
View Cart
GET /api/cart/user/{userId}
Update Cart Item
PUT /api/cart/update
Patch Quantity
PATCH /api/cart/{cartItemId}/quantity
Delete Cart Item
DELETE /api/cart/{cartItemId}
Coupon APIs
Apply Coupon
POST /api/coupons/apply
Checkout APIs
Checkout
POST /api/checkout
Order APIs
Get All Orders
GET /api/orders?page=0&size=5
Get Orders By User
GET /api/orders/user/{userId}
Get Order By Id
GET /api/orders/{orderId}
Order Status
PENDING
SUCCESS
FAILED
Coupon Types
PERCENTAGE
FLAT
Checkout Workflow
User adds products to cart.
User reviews cart details.
User applies coupon (optional).
Inventory validation is performed.
Order entity is created.
Payment simulation is executed.
If payment succeeds:
Inventory is reduced.
Cart is cleared.
Order status becomes SUCCESS.
If payment fails:
Order status becomes FAILED.
Order history becomes available for retrieval.
Pagination Example
First Page
GET /api/orders?page=0&size=5
Second Page
GET /api/orders?page=1&size=5
Page Size 3
GET /api/orders?page=0&size=3
Running the Application
Clone Repository
git clone <repository-url>
Navigate To Project
cd EcommerceCartCheckoutModule
Configure Database
Update the following properties inside:

src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Build Project
mvn clean install
Run Application
mvn spring-boot:run
Application starts on:

http://localhost:8080
Testing
The application can be tested using:

Postman Collection
MySQL Workbench
Swagger UI (if enabled)
Deliverables Included
Spring Boot Source Code
SQL Script
API Documentation
README File
Postman Collection
Unit Test Cases
Output Screenshots
Future Enhancements
Spring Security with JWT Authentication
Role Based Authorization
Payment Gateway Integration
Email Notifications
Product Categories
Wishlist Management
Order Cancellation
Refund Processing
Author
Poorvika

E-Commerce Cart & Checkout Module

Java Spring Boot Backend Development Assignment