package com.ecommerce.RequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplyCouponRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Coupon code is required")
    private String couponCode;
}