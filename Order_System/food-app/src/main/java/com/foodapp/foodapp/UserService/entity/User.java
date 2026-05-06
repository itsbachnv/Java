package com.foodapp.foodapp.UserService.entity;

import com.foodapp.foodapp.common.BaseEntity;
import com.foodapp.foodapp.common.Embeddable.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity @Table(name = "users")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName, lastName, phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // ACTIVE, INACTIVE, BANNED

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;
}
