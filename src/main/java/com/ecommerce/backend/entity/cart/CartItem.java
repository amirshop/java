package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.product.ProductVariant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int quantity;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

}
