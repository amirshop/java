package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    // A catalog can contain many products
    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
    private List<Product> products;
}
