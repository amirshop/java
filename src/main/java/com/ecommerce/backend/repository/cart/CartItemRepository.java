package com.ecommerce.backend.repository.cart;

import com.ecommerce.backend.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}
