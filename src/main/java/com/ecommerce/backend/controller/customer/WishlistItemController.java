package com.ecommerce.backend.controller.customer;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.WishlistItemDto;
import com.ecommerce.backend.service.customer.WishlistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wishlist-items")
@RequiredArgsConstructor
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @GetMapping
    public ResponseEntity<List<WishlistItemDto>> getAllWishlistItems() {
        List<WishlistItemDto> wishlistItems = wishlistItemService.getAllWishlistItems();
        return ResponseEntity.ok(wishlistItems);
    }

    @GetMapping("/{id}")
    public WishlistItemDto getWishlistItemById(@PathVariable UUID id) {
        return wishlistItemService.getWishlistItemById(id);
    }

    @PostMapping
    public ResponseEntity<WishlistItemDto> createWishlistItem(@RequestBody WishlistItemDto request) {
        WishlistItemDto createdWishlistItem = wishlistItemService.createWishlistItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlistItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WishlistItemDto> updateWishlistItem(@PathVariable UUID id, @RequestBody WishlistItemDto request) {
        WishlistItemDto updatedWishlistItem = wishlistItemService.updateWishlistItem(id, request);
        return updatedWishlistItem != null
                ? ResponseEntity.ok(updatedWishlistItem)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable UUID id) {
        wishlistItemService.deleteWishlistItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchWishlistItems(@RequestBody SearchDto requestDto) {
        return wishlistItemService.searchWishlistItems(requestDto);
    }
}
