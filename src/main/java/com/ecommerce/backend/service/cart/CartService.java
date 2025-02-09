package com.ecommerce.backend.service.cart;

import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository shoppingCartRepository;
    public List<Cart> getAllCarts() { return shoppingCartRepository.findAll(); }
}
