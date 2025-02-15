package com.ecommerce.backend.entity.account;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String label;

    @Column(length = 50, nullable = false)
    private String value;

    private Date createdAt;
    private Date updatedAt;
}
