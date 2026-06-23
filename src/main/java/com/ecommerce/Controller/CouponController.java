package com.ecommerce.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Repository.CartRepository;
import com.ecommerce.RequestDto.ApplyCouponRequest;
import com.ecommerce.ResponseDto.ApiResponse;
import com.ecommerce.ResponseDto.ApplyCouponResponse;
import com.ecommerce.ResponseDto.CouponResponse;
import com.ecommerce.Service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final CartRepository cartRepository;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<ApplyCouponResponse>> applyCoupon(@Valid @RequestBody ApplyCouponRequest request) {
        Cart cart = cartRepository
                .findByUserId(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found for user id : "+ request.getUserId()));

        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(this::calculateItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount=couponService.calculateDiscount(request.getCouponCode(),totalAmount);
        BigDecimal finalAmount =totalAmount.subtract(discountAmount);

        ApplyCouponResponse response= ApplyCouponResponse.builder()
        .couponCode(request.getCouponCode())
        .totalAmount(totalAmount)
        .discountAmount(discountAmount)
        .finalAmount(finalAmount)
        .build();

        return ResponseEntity.ok(new ApiResponse<>("Coupon applied successfully",response));
    }

    private BigDecimal calculateItemTotal(CartItem item) {
        return item.getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
    
    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}