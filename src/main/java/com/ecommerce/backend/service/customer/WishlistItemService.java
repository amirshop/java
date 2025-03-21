package com.ecommerce.backend.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.WishlistItem;
import com.ecommerce.backend.repository.customer.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistItemService {

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    public List<WishlistItem> findAll() {
        return wishlistItemRepository.findAll();
    }

    public Optional<WishlistItem> findById(UUID id) {
        return wishlistItemRepository.findById(id);
    }

    public WishlistItem create(WishlistItem item) {
        return wishlistItemRepository.save(item);
    }

    public WishlistItem update(UUID id, WishlistItem itemDetails) {
        return wishlistItemRepository.findById(id).map(item -> {
            item.setCustomer(itemDetails.getCustomer());
            item.setProduct(itemDetails.getProduct());
            item.setAddedAt(itemDetails.getAddedAt());
            return wishlistItemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("WishlistItem not found with id " + id));
    }

    public void delete(UUID id) {
        wishlistItemRepository.deleteById(id);
    }
}

