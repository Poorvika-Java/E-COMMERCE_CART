package com.ecommerce.ResponseDto;

import java.math.BigDecimal;
import com.ecommerce.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckoutResponse {

    private Long orderId;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private BigDecimal finalAmount;

    private String message;
}