package com.foodapp.foodapp.InventoryService.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "inventories")
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String variantId; // reference tới Product Service
    private String productId;
    private Integer quantityOnHand;    // tồn kho thực tế
    private Integer quantityReserved;  // đang giữ cho order
    private Integer quantityAvailable; // onHand - reserved
    private Integer lowStockThreshold; // trigger cảnh báo

    @Version
    private Long version; // Optimistic locking để tránh race condition

    @OneToMany(mappedBy = "inventory")
    private List<InventoryTransaction> transactions;
}