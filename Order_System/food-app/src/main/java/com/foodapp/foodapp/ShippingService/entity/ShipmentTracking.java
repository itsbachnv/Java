package com.foodapp.foodapp.ShippingService.entity;

import com.foodapp.foodapp.common.Embeddable.ShipmentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_trackings")
public class ShipmentTracking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "shipment_id")
    private Shipment shipment;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    private String location, description;
    private LocalDateTime timestamp;
}
