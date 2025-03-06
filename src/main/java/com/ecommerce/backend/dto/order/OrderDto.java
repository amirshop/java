package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.cart.ItemDto;
import com.ecommerce.backend.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String orderNumber;
    private String orderProcessor;
    private OrderStatus status;
    private Date orderDate;
    private List<PaymentResponseDto> payments;
    private List<ShipmentResponseDto> shipments;
    private List<ItemDto> items;
}

