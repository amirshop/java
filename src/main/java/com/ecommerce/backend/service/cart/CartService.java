package com.ecommerce.backend.service.cart;

import com.ecommerce.backend.dto.cart.request.ItemRequestDto;
import com.ecommerce.backend.dto.cart.response.CartResponseDto;
import com.ecommerce.backend.dto.cart.response.ItemResponseDto;
import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.cart.Item;
import com.ecommerce.backend.repository.cart.CartRepository;
import com.ecommerce.backend.repository.cart.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public CartResponseDto getCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .map(cart -> modelMapper.map(cart, CartResponseDto.class))
                .orElse(null);
    }

    public ItemResponseDto addItem(UUID cartId, ItemRequestDto itemRequest) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            return null; // Optionally, throw an exception
        }
        Cart cart = cartOpt.get();
        Item item = modelMapper.map(itemRequest, Item.class);
        item.setCart(cart);
        Item savedItem = itemRepository.save(item);
        return modelMapper.map(savedItem, ItemResponseDto.class);
    }

    public ItemResponseDto updateItem(UUID cartId, UUID itemId, ItemRequestDto itemRequest) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            if (!item.getCart().getId().equals(cartId)) {
                return null; // Ensuring the item belongs to the cart
            }
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(itemRequest.getPrice());
            Item updatedItem = itemRepository.save(item);
            return modelMapper.map(updatedItem, ItemResponseDto.class);
        }
        return null;
    }

    public void removeItem(UUID cartId, UUID itemId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            if (item.getCart().getId().equals(cartId)) {
                itemRepository.deleteById(itemId);
            }
        }
    }

    public void clearCart(UUID cartId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }
}

