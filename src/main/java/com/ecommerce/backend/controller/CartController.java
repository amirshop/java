package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.cart.CartDto;
import com.ecommerce.backend.dto.cart.ItemDto;
import com.ecommerce.backend.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID cartId) {
        CartDto cart = cartService.getCartById(cartId);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ItemDto> addItemToCart(@PathVariable UUID cartId, @RequestBody ItemDto itemRequest) {
        ItemDto createdItem = cartService.addItem(cartId, itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ItemDto> updateCartItem(@PathVariable UUID cartId, @PathVariable UUID itemId,
                                                  @RequestBody ItemDto itemRequest) {
        ItemDto updatedItem = cartService.updateItem(cartId, itemId, itemRequest);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable UUID cartId, @PathVariable UUID itemId) {
        cartService.removeItem(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}

