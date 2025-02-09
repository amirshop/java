package com.ecommerce.backend.repository.cart;

import com.ecommerce.backend.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

