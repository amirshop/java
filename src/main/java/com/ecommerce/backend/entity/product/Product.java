package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int availableItemCount;

    @ManyToOne
    private ProductCategory category;
}
