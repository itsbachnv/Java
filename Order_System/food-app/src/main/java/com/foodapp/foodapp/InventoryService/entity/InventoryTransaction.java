package com.foodapp.foodapp.InventoryService.entity;

import com.foodapp.foodapp.common.Embeddable.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_transactions")
public class InventoryTransaction {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    // IMPORT, EXPORT, RESERVE, RELEASE, ADJUSTMENT

    private Integer quantity; // dương = nhập, âm = xuất
    private Integer beforeQty, afterQty;
    private String referenceId;   // orderId, importId...
    private String referenceType; // ORDER, IMPORT, MANUAL
    private String note;
    private LocalDateTime createdAt;
}