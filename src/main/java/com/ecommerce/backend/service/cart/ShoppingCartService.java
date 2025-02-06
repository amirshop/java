package com.ecommerce.backend.service.cart;

import com.ecommerce.backend.entity.cart.ShoppingCart;
import com.ecommerce.backend.repository.cart.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    public List<ShoppingCart> getAllCarts() { return shoppingCartRepository.findAll(); }
}
