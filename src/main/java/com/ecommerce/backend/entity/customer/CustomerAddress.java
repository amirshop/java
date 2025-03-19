package com.ecommerce.backend.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer_addresses")
@Data
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String country;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private boolean isDefault; // آیا این آدرس، پیش‌فرض است؟
}
