package com.ecommerce.backend.dto.cart.response;

import com.ecommerce.backend.dto.product.response.ProductResponseDto;
import lombok.Data;

@Data
public class ItemResponseDto {
    private Long id;
    private int quantity;
    private double price;
    private ProductResponseDto product;
}

