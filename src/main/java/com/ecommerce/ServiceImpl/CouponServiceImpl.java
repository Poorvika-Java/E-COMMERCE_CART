package com.ecommerce.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ecommerce.Entity.Coupon;
import com.ecommerce.enums.DiscountType;
import com.ecommerce.Exception.CouponExpiredException;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Repository.CouponRepository;
import com.ecommerce.RequestDto.ApplyCouponRequest;
import com.ecommerce.ResponseDto.ApplyCouponResponse;
import com.ecommerce.ResponseDto.CouponResponse;
import com.ecommerce.Service.CouponService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public BigDecimal calculateDiscount(String couponCode,BigDecimal totalAmount) {
        if (couponCode == null || couponCode.isBlank()) {
            return BigDecimal.ZERO;
        }

        Coupon coupon = couponRepository
                .findByCode(couponCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Coupon not found : " + couponCode));

        if (!coupon.getActive()) {
            throw new CouponExpiredException("Coupon is inactive");
        }

        if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
            throw new CouponExpiredException("Coupon has expired");
        }

        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            return totalAmount
                    .multiply(coupon.getDiscountValue())
                    .divide(new BigDecimal("100"));
        }
        return coupon.getDiscountValue()
                .min(totalAmount);
    }

	@Override
	public ApplyCouponResponse applyCoupon(ApplyCouponRequest request) {
		return null;
	}
	
	@Override
	public List<CouponResponse> getAllCoupons() {

	    return couponRepository.findAll()
	            .stream()
	            .map(coupon -> CouponResponse.builder()
	                    .id(coupon.getId())
	                    .code(coupon.getCode())
	                    .discountType(coupon.getDiscountType())
	                    .discountValue(coupon.getDiscountValue())
	                    .expiryDate(coupon.getExpiryDate())
	                    .active(coupon.getActive())
	                    .build())
	            .toList();
	}
}