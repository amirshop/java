package com.ecommerce.backend.repository.customer;


import java.util.UUID;

import com.ecommerce.backend.entity.customer.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
}

