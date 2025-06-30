package com.ecommerce.backend.service.cart;

import com.ecommerce.backend.dto.cart.CartDto;
import com.ecommerce.backend.dto.cart.ItemDto;
import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.cart.CartItem;
import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.repository.cart.CartRepository;
import com.ecommerce.backend.service.customer.CustomerService;
import com.ecommerce.backend.service.product.ProductVariantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final ProductVariantService productVariantService;

    public CartDto getCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .orElse(null);
    }

    public Cart findCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElse(null);
    }

//    public ItemDto  addItem(ItemDto itemRequest) {
//        Customer customer = getCustomer();
//
//
//        CartItem item = modelMapper.map(itemRequest, CartItem.class);
//        item.setCart(cart);
//        CartItem savedItem = itemRepository.save(item);
//        return modelMapper.map(savedItem, ItemDto.class);
//    }

    /**
     * Adds items to cart for authenticated users only
     *
     * @param productId The ID of the product to add
     * @param quantity The quantity to add
     * @return The updated cart
     */
//    @Transactional
//    public Cart addItemToCart(UUID productId, int quantity) {
//        Customer customer = customerService.getCustomerByAuthenticationToken();
//        return addItemToCustomerCart(productId, quantity, customer.getId());
//    }


    /**
     * Adds a product variant to cart for authenticated users
     *
     * @param productVariantId The ID of the product variant to add
     * @param quantity The quantity to add
     * @return The updated cart
//     * @throws ProductVariantNotFoundException if variant doesn't exist
//     * @throws CartException if user is not authenticated
     */
    @Transactional
    public Cart addItemToCart(UUID productVariantId, int quantity) {
//            throws ProductVariantNotFoundException, CartException {

        Customer customer = customerService.getCustomerByAuthenticationToken();

        // Validate product variant exists and is available
        ProductVariant variant = productVariantService.getAvailableVariant(productVariantId);

        Cart cart = getOrCreateCustomerCart(customer.getId());

        // Check if this variant already exists in cart
        cart.getItems().stream()
                .filter(item -> item.getVariant().getId().equals(productVariantId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + quantity);
//                            cartItemService.save(item); //fixme: add save method in cartItemService
                        },
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setQuantity(quantity);
                            newItem.setVariant(variant);
                            cart.getItems().add(newItem);
//                            cartItemService.save(newItem); //fixme: add save method in cartItemService
                        }
                );

        return cartRepository.save(cart);
    }

    private Cart getOrCreateCustomerCart(UUID customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
//                    newCart.setCustomer(customerId); //fixme: set customer instead of customer Id
                    return cartRepository.save(newCart);
                });
    }

    private Cart addItemToCustomerCart(UUID productId, int quantity, UUID customerId) {
        Cart cart = getOrCreateCustomerCart(customerId);

        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + quantity),
                        () -> cart.getItems().add(new CartItem(productId, quantity))
                );

        return cartRepository.save(cart);
    }

    public ItemDto updateItem(UUID cartId, UUID itemId, ItemDto itemRequest) {
        Optional<CartItem> itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (!item.getCart().getId().equals(cartId)) {
                return null; // Ensuring the item belongs to the cart
            }
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(itemRequest.getPrice());
            CartItem updatedItem = itemRepository.save(item);
            return modelMapper.map(updatedItem, ItemDto.class);
        }
        return null;
    }

    public void removeItem(UUID cartId, UUID itemId) {
        Optional<CartItem> itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
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





