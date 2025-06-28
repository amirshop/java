package com.ecommerce.backend.mapper.order;

import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.entity.product.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
}
