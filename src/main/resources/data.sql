-- USERS 

INSERT INTO users(name,email) VALUES
('Poorvika','poorvika@gmail.com'),
('Rahul','rahul@gmail.com'),
('Priya','priya@gmail.com'),
('Arjun','arjun@gmail.com'),
('Sneha','sneha@gmail.com'),
('Kiran','kiran@gmail.com');


-- PRODUCTS 

INSERT INTO products(name,price,stock_quantity) VALUES
('Laptop',50000.00,10),
('Mobile',25000.00,20),
('Headphones',3000.00,50),
('Keyboard',1500.00,40),
('Mouse',800.00,60),
('Monitor',12000.00,15);


-- CARTS 

INSERT INTO carts(user_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6);


-- CART ITEMS 

INSERT INTO cart_items(cart_id,product_id,quantity) VALUES
(1,1,1),
(1,3,2),
(2,2,1),
(3,4,3),
(4,5,2),
(5,6,1);


-- COUPONS 

INSERT INTO coupons
(code,discount_type,discount_value,expiry_date,active)VALUES
('SAVE10','PERCENTAGE',10.00,'2027-12-31',TRUE),

('FLAT500','FLAT',500.00,'2027-12-31',TRUE),

('OLD10','PERCENTAGE',10.00,'2024-01-01',TRUE),

('INACTIVE500','FLAT',500.00,'2027-12-31',FALSE),

('SAVE20','PERCENTAGE',20.00,'2027-12-31',TRUE),

('FLAT1000','FLAT',1000.00,'2027-12-31',TRUE);


INSERT INTO orders
(user_id,total_amount,discount_amount,final_amount,status,created_at)VALUES
(1,50000,5000,45000,'SUCCESS',NOW()),

(2,25000,500,24500,'SUCCESS',NOW()),

(3,3000,0,3000,'PENDING',NOW()),

(4,4500,900,3600,'SUCCESS',NOW()),

(5,12000,1000,11000,'FAILED',NOW()),

(6,800,0,800,'SUCCESS',NOW());


INSERT INTO order_items(order_id,product_id,quantity,price)VALUES
(1,1,1,50000),
(2,2,1,25000),
(3,3,1,3000),
(4,4,3,1500),
(5,6,1,12000),
(6,5,1,800);