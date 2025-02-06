package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    // Link to product
    @ManyToOne
    private Product product;

    // Link back to the shopping cart
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
}
