# API DOCUMENTATION

## Project Name

E-Commerce Cart & Checkout Module

## Base URL


http://localhost:8080


---

# Authentication

This project does not implement authentication or authorization. All APIs are publicly accessible for demonstration purposes.

---

# 1. Add Product To Cart

## Endpoint


POST /api/cart/add


## Request Body


{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}


## Success Response


{
  "message": "Product added to cart successfully",
  "data": {
    "cartId": 1,
    "userId": 1,
    "totalPrice": 100000
  }
}


## Status Codes

| Code | Description                |
| ---- | -------------------------- |
| 201  | Product Added Successfully |
| 404  | User Not Found             |
| 404  | Product Not Found          |
| 400  | Invalid Request            |

---

# 2. View Cart

## Endpoint


GET /api/cart/user/{userId}


## Example

GET /api/cart/user/1


## Success Response


{
  "message": "Cart fetched successfully",
  "data": {
    "cartId": 1,
    "userId": 1,
    "totalPrice": 56000,
    "items": [
      {
        "productId": 1,
        "productName": "Laptop",
        "quantity": 1,
        "unitPrice": 50000,
        "totalPrice": 50000
      },
      {
        "productId": 3,
        "productName": "Headphones",
        "quantity": 2,
        "unitPrice": 3000,
        "totalPrice": 6000
      }
    ]
  }
}


## Status Codes

| Code | Description    |
| ---- | -------------- |
| 200  | Success        |
| 404  | Cart Not Found |

---

# 3. Update Cart Item

## Endpoint


PUT /api/cart/update


## Request Body


{
  "cartItemId": 1,
  "quantity": 3
}


## Success Response


{
  "message": "Cart item updated successfully"
}


## Status Codes

| Code | Description          |
| ---- | -------------------- |
| 200  | Updated Successfully |
| 404  | Cart Item Not Found  |

---

# 4. Partial Quantity Update

## Endpoint


PATCH /api/cart/{cartItemId}/quantity


## Example


PATCH /api/cart/1/quantity?quantity=5


## Success Response


{
  "message": "Cart quantity updated successfully"
}


---

# 5. Remove Cart Item

## Endpoint


DELETE /api/cart/{cartItemId}


## Example


DELETE /api/cart/1


## Success Response


{
  "message": "Cart item removed successfully",
  "data": "Deleted"
}


## Status Codes

| Code | Description          |
| ---- | -------------------- |
| 200  | Deleted Successfully |
| 404  | Cart Item Not Found  |

---

# 6. Apply Coupon

## Endpoint


POST /api/coupons/apply


## Request Body


{
  "userId": 1,
  "couponCode": "SAVE10"
}


## Success Response


{
  "message": "Coupon applied successfully",
  "data": {
    "couponCode": "SAVE10",
    "totalAmount": 5000,
    "discountAmount": 500,
    "finalAmount": 4500
  }
}


## Status Codes

| Code | Description      |
| ---- | ---------------- |
| 200  | Coupon Applied   |
| 400  | Coupon Expired   |
| 400  | Coupon Inactive  |
| 404  | Coupon Not Found |

---

# 7. Checkout

## Endpoint

POST /api/checkout


## Request Body


{
  "userId": 1,
  "couponCode": "SAVE10",
  "paymentMode": "UPI"
}


## Success Response


{
  "message": "Checkout completed",
  "data": {
    "orderId": 1,
    "status": "SUCCESS",
    "totalAmount": 5000,
    "discountAmount": 500,
    "finalAmount": 4500,
    "message": "Payment successful. Order created."
  }
}


## Payment Failure Response


{
  "message": "Checkout completed",
  "data": {
    "orderId": 2,
    "status": "FAILED",
    "totalAmount": 150000,
    "discountAmount": 0,
    "finalAmount": 150000,
    "message": "Payment failed."
  }
}


## Status Codes

| Code | Description        |
| ---- | ------------------ |
| 200  | Checkout Completed |
| 400  | Cart Empty         |
| 400  | Payment Failed     |
| 409  | Insufficient Stock |
| 404  | User Not Found     |

---

# 8. Get Order History

## Endpoint


GET /api/orders/user/{userId}


## Example

GET /api/orders/user/1?page=0&size=5


## Success Response


{
  "message": "Order history fetched successfully",
  "data": {
    "content": [
      {
        "orderId": 1,
        "userId": 1,
        "totalAmount": 5000,
        "discountAmount": 500,
        "finalAmount": 4500,
        "status": "SUCCESS",
        "createdAt": "2026-06-22T10:15:00"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "size": 5,
    "number": 0
  }
}


## Status Codes

| Code | Description    |
| ---- | -------------- |
| 200  | Success        |
| 404  | User Not Found |

---

# 9. Get Order By ID

## Endpoint


GET /api/orders/{orderId}


## Example


GET /api/orders/1


## Success Response


{
  "message": "Order fetched successfully",
  "data": {
    "orderId": 1,
    "userId": 1,
    "totalAmount": 5000,
    "discountAmount": 500,
    "finalAmount": 4500,
    "status": "SUCCESS",
    "createdAt": "2026-06-22T10:15:00",
    "items": [
      {
        "productId": 1,
        "productName": "Laptop",
        "quantity": 1,
        "price": 50000
      }
    ]
  }
}


## Status Codes

| Code | Description     |
| ---- | --------------- |
| 200  | Success         |
| 404  | Order Not Found |

---

# Global Error Response

{
  "timestamp": "2026-06-22T11:00:00",
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Order not found with id : 100"
}


---

# Sample Coupons

| Coupon Code | Type       | Discount |
| ----------- | ---------- | -------- |
| SAVE10      | Percentage | 10%      |
| FLAT500     | Flat       | ₹500     |
| OLD10       | Expired    | 10%      |
| INACTIVE500 | Inactive   | ₹500     |

---

# Business Workflow

1. User adds products to cart.
2. User views cart.
3. User updates or removes cart items.
4. User applies a coupon.
5. System validates available inventory.
6. Checkout process begins.
7. Order is created with PENDING status.
8. Payment simulation is executed.
9. If payment succeeds:

   * Inventory is reduced.
   * Cart is cleared.
   * Order status becomes SUCCESS.
10. If payment fails:

    * Order status becomes FAILED.
11. User can retrieve order history and order details.

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
