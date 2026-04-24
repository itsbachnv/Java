package com.foodapp.foodapp.ProductService.entity;

import com.foodapp.foodapp.common.Embeddable.ProductStatus;
import com.foodapp.foodapp.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name, slug, description;
    private String sellerId; // reference to User Service
    @ManyToOne @JoinColumn(name = "category_id")
    private Category category;
    private BigDecimal basePrice;
    @Enumerated(EnumType.STRING)
    private ProductStatus status; // DRAFT, PUBLISHED, SUSPENDED

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariant> variants;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAttribute> attributes;
}