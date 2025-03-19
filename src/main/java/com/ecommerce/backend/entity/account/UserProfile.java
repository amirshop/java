package com.ecommerce.backend.entity.account;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    private String firstName;
    private String lastName;
    private String address;
    private String profilePictureUrl;

}

