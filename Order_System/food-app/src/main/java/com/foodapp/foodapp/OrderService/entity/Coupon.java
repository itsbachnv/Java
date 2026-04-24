package com.foodapp.foodapp.OrderService.entity;

import com.foodapp.foodapp.common.Embeddable.DiscountType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true) private String code;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // PERCENTAGE, FIXED_AMOUNT
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private BigDecimal maxDiscountAmount;
    private Integer usageLimit, usedCount;
    private LocalDateTime validFrom, validTo;
    private boolean active;
}