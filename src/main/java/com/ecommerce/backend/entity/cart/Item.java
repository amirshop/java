package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String name;
    private int quantity;
    private double price;

    // Link to product
    @ManyToOne
    private Product product;

    // Link back to the cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
