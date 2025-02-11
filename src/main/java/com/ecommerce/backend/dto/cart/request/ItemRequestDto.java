package com.ecommerce.backend.dto.cart.request;

import lombok.Data;

@Data
public class ItemRequestDto {
    private Long productId;
    private int quantity;
    private double price;
}

