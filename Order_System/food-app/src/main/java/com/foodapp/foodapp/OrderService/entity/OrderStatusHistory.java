package com.foodapp.foodapp.OrderService.entity;

import com.foodapp.foodapp.common.Embeddable.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_histories")
public class OrderStatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
    @Enumerated(EnumType.STRING)
    private OrderStatus fromStatus, toStatus;
    private String changedBy, reason;
    private LocalDateTime createdAt;
}