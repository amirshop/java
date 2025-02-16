package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.cart.request.ItemRequestDto;
import com.ecommerce.backend.dto.cart.response.CartResponseDto;
import com.ecommerce.backend.dto.cart.response.ItemResponseDto;
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
    public ResponseEntity<CartResponseDto> getCartById(@PathVariable UUID cartId) {
        CartResponseDto cart = cartService.getCartById(cartId);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ItemResponseDto> addItemToCart(@PathVariable UUID cartId, @RequestBody ItemRequestDto itemRequest) {
        ItemResponseDto createdItem = cartService.addItem(cartId, itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ItemResponseDto> updateCartItem(@PathVariable UUID cartId, @PathVariable UUID itemId, @RequestBody ItemRequestDto itemRequest) {
        ItemResponseDto updatedItem = cartService.updateItem(cartId, itemId, itemRequest);
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

