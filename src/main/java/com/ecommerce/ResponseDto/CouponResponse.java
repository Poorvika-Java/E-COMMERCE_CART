package com.ecommerce.ResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.ecommerce.enums.DiscountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponse {

    private Long id;
    private String code;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private LocalDate expiryDate;
    private Boolean active;
}