package com.ecommerce.RequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    private String couponCode;

    private String paymentMode;
}