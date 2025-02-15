package com.ecommerce.backend.dto.cart.response;

import com.ecommerce.backend.dto.product.response.ProductResponseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemResponseDto {
    private UUID id;
    private int quantity;
    private double price;
    private ProductResponseDto product;
}

