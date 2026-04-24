package com.foodapp.foodapp.UserService.entity;

import com.foodapp.foodapp.common.Embeddable.AddressType;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    private String fullName, phone, street, ward, district, province;
    private boolean isDefault;
    @Enumerated(EnumType.STRING)
    private AddressType type; // HOME, WORK, OTHER
}
