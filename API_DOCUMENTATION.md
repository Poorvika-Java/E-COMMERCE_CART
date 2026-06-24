# API DOCUMENTATION

## Project Name

E-Commerce Cart & Checkout Module

## Base URL

http://localhost:8080

---

# Authentication

This project does not implement authentication or authorization. All APIs are publicly accessible for demonstration purposes.

---

# USER APIs

## Create User

### Endpoint

POST /api/users

### Request Body

```json
{
  "name": "Poorvika",
  "email": "poorvika@gmail.com"
}
```

### Success Response

```json
{
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "Poorvika",
    "email": "poorvika@gmail.com"
  }
}
```

---

## Get All Users

### Endpoint

GET /api/users

### Success Response

```json
{
  "message": "Users fetched successfully",
  "data": [
    {
      "id": 1,
      "name": "Poorvika",
      "email": "poorvika@gmail.com"
    }
  ]
}
```

---

## Get User By Id

### Endpoint

GET /api/users/{id}

### Example

GET /api/users/1

### Status Codes

| Code | Description    |
| ---- | -------------- |
| 200  | Success        |
| 404  | User Not Found |

---

# PRODUCT APIs

## Create Product

### Endpoint

POST /api/products

### Request Body

```json
{
  "name": "Laptop",
  "price": 50000,
  "stockQuantity": 10
}
```

### Success Response

```json
{
  "message": "Product created successfully"
}
```

---

## Get All Products

### Endpoint

GET /api/products

### Status Codes

| Code | Description |
| ---- | ----------- |
| 200  | Success     |

---

## Get Product By Id

### Endpoint

GET /api/products/{id}

### Example

GET /api/products/1

### Status Codes

| Code | Description       |
| ---- | ----------------- |
| 200  | Success           |
| 404  | Product Not Found |

---

# CART APIs

## Add Product To Cart

### Endpoint

POST /api/cart/add

### Request Body

```json
{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}
```

### Status Codes

| Code | Description                |
| ---- | -------------------------- |
| 201  | Product Added Successfully |
| 404  | User Not Found             |
| 404  | Product Not Found          |
| 400  | Invalid Request            |

---

## View Cart

### Endpoint

GET /api/cart/user/{userId}

### Example

GET /api/cart/user/1

### Status Codes

| Code | Description    |
| ---- | -------------- |
| 200  | Success        |
| 404  | Cart Not Found |

---

## Update Cart Item

### Endpoint

PUT /api/cart/update

### Request Body

```json
{
  "cartItemId": 1,
  "quantity": 3
}
```

### Status Codes

| Code | Description          |
| ---- | -------------------- |
| 200  | Updated Successfully |
| 404  | Cart Item Not Found  |

---

## Partial Quantity Update

### Endpoint

PATCH /api/cart/{cartItemId}/quantity

### Example

PATCH /api/cart/1/quantity?quantity=5

---

## Remove Cart Item

### Endpoint

DELETE /api/cart/{cartItemId}

### Example

DELETE /api/cart/1

---

# COUPON APIs

## Apply Coupon

### Endpoint

POST /api/coupons/apply

### Request Body

```json
{
  "userId": 1,
  "couponCode": "SAVE10"
}
```

### Status Codes

| Code | Description      |
| ---- | ---------------- |
| 200  | Coupon Applied   |
| 400  | Coupon Expired   |
| 400  | Coupon Inactive  |
| 404  | Coupon Not Found |

---

# CHECKOUT APIs

## Checkout

### Endpoint

POST /api/checkout

### Request Body

```json
{
  "userId": 1,
  "couponCode": "SAVE10",
  "paymentMode": "UPI"
}
```

### Status Codes

| Code | Description        |
| ---- | ------------------ |
| 200  | Checkout Completed |
| 400  | Cart Empty         |
| 400  | Payment Failed     |
| 409  | Insufficient Stock |
| 404  | User Not Found     |

---

# ORDER APIs

## Get All Orders

### Endpoint

GET /api/orders

### Example

GET /api/orders?page=0&size=5

### Status Codes

| Code | Description |
| ---- | ----------- |
| 200  | Success     |

---

## Get Order History By User Id

### Endpoint

GET /api/orders/user/{userId}/all

### Example

GET /api/orders/user/1/all

### Status Codes

| Code | Description    |
| ---- | -------------- |
| 200  | Success        |
| 404  | User Not Found |

---

## Get Order By Id

### Endpoint

GET /api/orders/{orderId}

### Example

GET /api/orders/1

### Status Codes

| Code | Description     |
| ---- | --------------- |
| 200  | Success         |
| 404  | Order Not Found |

---

# Global Error Response

```json
{
  "timestamp": "2026-06-22T11:00:00",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Order not found with id : 100"
}
```

---

# Sample Coupons

| Coupon Code | Type       | Discount |
| ----------- | ---------- | -------- |
| SAVE10      | Percentage | 10%      |
| FLAT500     | Flat       | ₹500     |
| OLD10       | Expired    | 10%      |
| INACTIVE500 | Inactive   | ₹500     |

---

# Database Tables

* Users
* Products
* Carts
* Cart_Items
* Orders
* Order_Items
* Coupons

---

# Business Workflow

1. User adds products to cart.
2. User views cart.
3. User updates or removes cart items.
4. User applies a coupon.
5. System validates inventory.
6. Checkout begins.
7. Order is created with PENDING status.
8. Payment simulation executes.
9. On SUCCESS:

   * Inventory reduced.
   * Cart cleared.
   * Order marked SUCCESS.
10. On FAILURE:

* Inventory restored.
* Order marked FAILED.

11. User retrieves order history and order details.

---

# Technologies Used

* Java 21
* Spring Boot 3
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* Maven
* JUnit 5
* Mockito
* Postman

---

# Author

Poorvika HR

E-Commerce Cart & Checkout Module Assignment Submission
