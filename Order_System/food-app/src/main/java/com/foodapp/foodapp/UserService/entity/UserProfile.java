package com.foodapp.foodapp.UserService.entity;

import com.foodapp.foodapp.common.Embeddable.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity @Table(name = "user_profiles")
public class UserProfile {
    @Id private String userId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") private User user;
    private String avatarUrl, bio;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
