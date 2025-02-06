package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    // A catalog can contain many products
    @OneToMany
    @JoinColumn(name = "catalog_id")
    private List<Product> listOfProducts;
}
