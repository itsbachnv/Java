package com.foodapp.foodapp.ProductService.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
public class ProductVariant {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String sku; // unique per variant
    private String name; // "Đỏ / XL"
    private BigDecimal price;
    private BigDecimal salePrice;
    private Double weight;
    private String imageUrl;
    // Stock quantity được quản lý bởi Inventory Service
}