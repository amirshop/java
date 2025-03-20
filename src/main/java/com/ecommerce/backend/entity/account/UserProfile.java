package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender; // مرد/زن/نامشخص

    @Embedded
    private Address address;

    private String profilePictureUrl;

}

