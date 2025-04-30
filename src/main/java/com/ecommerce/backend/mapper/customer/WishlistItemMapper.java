package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.WishlistItemDto;
import com.ecommerce.backend.entity.customer.WishlistItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistItemMapper {

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "product.id", source = "productId")
    WishlistItem toEntity(WishlistItemDto wishlistItemDto);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "productId", source = "product.id")
    WishlistItemDto toDto(WishlistItem wishlistItem);
}
