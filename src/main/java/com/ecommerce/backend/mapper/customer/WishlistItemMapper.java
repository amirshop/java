package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.WishlistItemDto;
import com.ecommerce.backend.entity.customer.WishlistItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistItemMapper {

    WishlistItem toEntity(WishlistItemDto wishlistItemDto);
    WishlistItemDto toDto(WishlistItem wishlistItem);
}
