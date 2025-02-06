package com.ecommerce.backend.repository.cart;

import com.ecommerce.backend.entity.cart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}

