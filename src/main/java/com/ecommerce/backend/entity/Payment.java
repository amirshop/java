package com.ecommerce.backend.entity;

import com.ecommerce.backend.enums.PaymentStatus;
import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private double amount;
}
