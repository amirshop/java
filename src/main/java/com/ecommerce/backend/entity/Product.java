package com.ecommerce.backend.entity;

import jakarta.persistence.*;

@Entity
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
