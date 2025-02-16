package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;
}
