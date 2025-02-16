package com.ecommerce.backend.repository.cart;

import com.ecommerce.backend.entity.cart.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
