package com.ecommerce.backend.service.cart;


import com.ecommerce.backend.entity.cart.Item;
import com.ecommerce.backend.repository.cart.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public List<Item> getAllItems() { return itemRepository.findAll(); }
}
