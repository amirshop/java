package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.cart.CartDto;
import com.ecommerce.backend.entity.cart.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toEntity(CartDto cartDto);
    CartDto toDto(Cart cart);
}
