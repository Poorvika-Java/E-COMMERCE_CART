package com.ecommerce.Exception;

public class CartEmptyException extends RuntimeException {

    public CartEmptyException(String message) {
        super(message);
    }
}