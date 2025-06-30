package com.ecommerce.backend.service.cart;


import com.ecommerce.backend.entity.cart.CartItem;
import com.ecommerce.backend.repository.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository carItemRepository;
    public List<CartItem> getAllItems() { return carItemRepository.findAll(); }

    public Optional<CartItem> findById(UUID itemId) {
        return null;
    }

    public CartItem save(CartItem item) {
        return null;
    }

    public void deleteById(UUID itemId) {
    }
}
