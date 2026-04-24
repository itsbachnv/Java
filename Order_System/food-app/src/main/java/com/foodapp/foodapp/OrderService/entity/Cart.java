package com.foodapp.foodapp.OrderService.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    private String userId; // 1 user = 1 cart
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;
    private LocalDateTime updatedAt;
}