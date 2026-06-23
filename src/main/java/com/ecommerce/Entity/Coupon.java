package com.ecommerce.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.ecommerce.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coupons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Boolean active;
}