package com.ecommerce.backend.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String slug;
    private String description;
}
