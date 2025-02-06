package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.entity.cart.ShoppingCart;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("CUSTOMER")
@Data
public class Customer extends Account {

    // One customer can have one shopping cart (or you could make it OneToMany if needed)
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    // Example: a method "searchProduct(searchKey)" would typically be in a service, not an entity.
}
