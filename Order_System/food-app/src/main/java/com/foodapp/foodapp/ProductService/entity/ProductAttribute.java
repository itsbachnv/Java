package com.foodapp.foodapp.ProductService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_attributes")
public class ProductAttribute {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;
    private String name;  // "Màu sắc", "Kích cỡ"
    private String value; // "Đỏ", "XL"
}