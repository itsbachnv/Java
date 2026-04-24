package com.foodapp.foodapp.OrderService.entity;

import com.foodapp.foodapp.common.Embeddable.OrderStatus;
import com.foodapp.foodapp.common.Embeddable.PaymentMethod;
import com.foodapp.foodapp.common.Embeddable.PaymentStatus;
import com.foodapp.foodapp.common.Embeddable.ShippingAddress;
import com.foodapp.foodapp.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "orders")
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String orderCode; // ORD-20250423-XXXXX

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    // PENDING → CONFIRMED → PROCESSING → SHIPPED → DELIVERED → COMPLETED
    // PENDING → CANCELLED
    // COMPLETED → RETURN_REQUESTED → RETURNED

    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Embedded
    private ShippingAddress shippingAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // COD, VNPAY, MOMO, BANKING

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // PENDING, PAID, REFUNDED

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    private List<OrderStatusHistory> statusHistories;

    private String note;
    private LocalDateTime confirmedAt, shippedAt, deliveredAt;
}