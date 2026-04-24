package com.foodapp.foodapp.ProductService.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, slug, description, imageUrl;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> children;
    private Integer sortOrder;
    private boolean active;
}