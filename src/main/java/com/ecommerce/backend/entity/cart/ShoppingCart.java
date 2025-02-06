package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.account.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@DiscriminatorValue("SHOPPING_CART")
@Data
public class ShoppingCart extends Cart {

    // Link back to the customer
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // A shopping cart can have many items
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<Item> items;
}
