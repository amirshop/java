package com.ecommerce.backend.entity.cart;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cart_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isClosed;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
