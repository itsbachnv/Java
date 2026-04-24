package com.foodapp.foodapp.PaymentService.entity;

import com.foodapp.foodapp.common.Embeddable.PaymentMethod;
import com.foodapp.foodapp.common.Embeddable.PaymentStatus;
import com.foodapp.foodapp.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String orderId;
    private String userId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // VNPAY, MOMO, COD, BANKING

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // PENDING, SUCCESS, FAILED, REFUNDED

    private String transactionId;   // ID từ payment gateway
    private String gatewayResponse; // raw JSON từ gateway
    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "payment")
    private List<Refund> refunds;
}