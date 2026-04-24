package com.foodapp.foodapp.InventoryService.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, code, address;
    private boolean active;
}