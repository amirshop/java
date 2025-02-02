package com.ecommerce.backend.entity;

import com.ecommerce.backend.enums.AccountStatus;
import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String name;

    @Embedded
    private Address shippingAddress;

    private String email;
    private String phone;
}


