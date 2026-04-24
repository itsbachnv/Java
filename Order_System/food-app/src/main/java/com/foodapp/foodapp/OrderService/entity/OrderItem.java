package com.foodapp.foodapp.OrderService.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;
    private String productId, variantId;  // snapshot từ Product Service
    private String productName, variantName, imageUrl, sku;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
}