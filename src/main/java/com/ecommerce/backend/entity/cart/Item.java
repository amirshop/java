package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

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
