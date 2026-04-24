package com.foodapp.foodapp.ShippingService.entity;

import com.foodapp.foodapp.common.Embeddable.ShipmentStatus;
import com.foodapp.foodapp.common.Embeddable.ShippingAddress;
import com.foodapp.foodapp.common.Embeddable.ShippingProvider;
import com.foodapp.foodapp.common.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String orderId;
    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Enumerated(EnumType.STRING)
    private ShippingProvider provider;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fullName", column = @Column(name = "sender_full_name")),
            @AttributeOverride(name = "phone",    column = @Column(name = "sender_phone")),
            @AttributeOverride(name = "street",   column = @Column(name = "sender_street")),
            @AttributeOverride(name = "ward",     column = @Column(name = "sender_ward")),
            @AttributeOverride(name = "district", column = @Column(name = "sender_district")),
            @AttributeOverride(name = "province", column = @Column(name = "sender_province"))
    })
    private ShippingAddress senderAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "fullName", column = @Column(name = "receiver_full_name")),
            @AttributeOverride(name = "phone",    column = @Column(name = "receiver_phone")),
            @AttributeOverride(name = "street",   column = @Column(name = "receiver_street")),
            @AttributeOverride(name = "ward",     column = @Column(name = "receiver_ward")),
            @AttributeOverride(name = "district", column = @Column(name = "receiver_district")),
            @AttributeOverride(name = "province", column = @Column(name = "receiver_province"))
    })
    private ShippingAddress receiverAddress;

    private BigDecimal shippingFee;
    private Double totalWeight;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    @OrderBy("timestamp ASC")
    private List<ShipmentTracking> trackings;
}