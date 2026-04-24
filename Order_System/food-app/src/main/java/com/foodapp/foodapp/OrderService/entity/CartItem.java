package com.foodapp.foodapp.OrderService.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private String productId, variantId;
    private Integer quantity;
    private LocalDateTime addedAt;
}