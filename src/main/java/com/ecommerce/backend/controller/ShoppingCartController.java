package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.cart.ShoppingCart;
import com.ecommerce.backend.service.cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartService.getAllCarts();
    }
}
