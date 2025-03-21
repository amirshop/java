package com.ecommerce.backend.controller.customer;

import java.util.List;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.WishlistItem;
import com.ecommerce.backend.service.customer.WishlistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {

    @Autowired
    private WishlistItemService wishlistItemService;

    @GetMapping
    public List<WishlistItem> getAll() {
        return wishlistItemService.findAll();
    }

    @GetMapping("/{id}")
    public WishlistItem getById(@PathVariable UUID id) {
        return wishlistItemService.findById(id)
                .orElseThrow(() -> new RuntimeException("WishlistItem not found with id " + id));
    }

    @PostMapping
    public WishlistItem create(@RequestBody WishlistItem item) {
        return wishlistItemService.create(item);
    }

    @PutMapping("/{id}")
    public WishlistItem update(@PathVariable UUID id, @RequestBody WishlistItem item) {
        return wishlistItemService.update(id, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        wishlistItemService.delete(id);
    }
}
