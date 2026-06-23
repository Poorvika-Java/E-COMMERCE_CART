package com.ecommerce.Service;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.RequestDto.ApplyCouponRequest;
import com.ecommerce.ResponseDto.ApplyCouponResponse;
import com.ecommerce.ResponseDto.CouponResponse;

public interface CouponService {

    BigDecimal calculateDiscount(String couponCode, BigDecimal totalAmount);

    ApplyCouponResponse applyCoupon(ApplyCouponRequest request);

    List<CouponResponse> getAllCoupons();
}
