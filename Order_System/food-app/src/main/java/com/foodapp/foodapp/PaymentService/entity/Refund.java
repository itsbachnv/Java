package com.foodapp.foodapp.PaymentService.entity;

import com.foodapp.foodapp.common.Embeddable.RefundStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "refunds")
public class Refund {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private RefundStatus status; // PENDING, PROCESSING, COMPLETED, FAILED
    private String reason;
    private String transactionId;
    private LocalDateTime processedAt;
}