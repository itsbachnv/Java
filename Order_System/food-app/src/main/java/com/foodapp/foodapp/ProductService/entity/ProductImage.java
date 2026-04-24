package com.foodapp.foodapp.ProductService.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String url;

    private String altText;

    private Integer sortOrder; // thứ tự hiển thị

    private boolean isPrimary; // ảnh đại diện chính

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}