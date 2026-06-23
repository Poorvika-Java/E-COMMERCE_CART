USE ecommercedb;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS coupons;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);


CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    stock_quantity INT NOT NULL
);


CREATE TABLE carts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_cart_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);


CREATE TABLE cart_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_item_cart
    FOREIGN KEY (cart_id)
    REFERENCES carts(id),
    CONSTRAINT fk_cart_item_product
    FOREIGN KEY (product_id)
    REFERENCES products(id)
);


CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    discount_amount DECIMAL(12,2) NOT NULL,
    final_amount DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME,
    CONSTRAINT fk_order_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);


CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_order_item_order
    FOREIGN KEY (order_id)
    REFERENCES orders(id),
    CONSTRAINT fk_order_item_product
    FOREIGN KEY (product_id)
    REFERENCES products(id)
);


CREATE TABLE coupons (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount_type VARCHAR(20) NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    expiry_date DATE NOT NULL,
    active BOOLEAN NOT NULL
);