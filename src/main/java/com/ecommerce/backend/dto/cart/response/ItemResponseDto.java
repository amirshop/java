package com.ecommerce.backend.dto.cart.response;

import com.ecommerce.backend.dto.product.ProductDto;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemResponseDto {
    private UUID id;
    private int quantity;
    private double price;
    private ProductDto product;
}

