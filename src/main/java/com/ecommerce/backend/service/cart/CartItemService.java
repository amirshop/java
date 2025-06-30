package com.ecommerce.backend.service.cart;


import com.ecommerce.backend.entity.cart.CartItem;
import com.ecommerce.backend.repository.cart.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository carItemRepository;
    public List<CartItem> getAllItems() { return carItemRepository.findAll(); }
}
