package com.ecommerce.backend.mapper.cart;

import com.ecommerce.backend.dto.cart.CartDto;
import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.cart.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "items", source = "items", qualifiedByName = "mapUUIDsToItems")
    Cart toEntity(CartDto cartDto);

    @Mapping(target = "items", source = "items", qualifiedByName = "mapItemsToUUIDs")
    CartDto toDto(Cart cart);

    @Named("mapItemsToUUIDs")
    default List<UUID> mapItemsToUUIDs(List<Item> items) {
        return items != null
                ? items.stream().map(Item::getId).collect(Collectors.toList())
                : new ArrayList<>();
    }

    @Named("mapUUIDsToItems")
    default List<Item> mapUUIDsToItems(List<UUID> itemIds) {
        return itemIds != null
                ? itemIds.stream()
                .map(id -> {
                    Item item = new Item();
                    item.setId(id);
                    return item;
                })
                .collect(Collectors.toList())
                : new ArrayList<>();
    }
}

