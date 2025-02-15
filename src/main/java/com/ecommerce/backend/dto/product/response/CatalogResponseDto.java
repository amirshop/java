package com.ecommerce.backend.dto.product.response;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CatalogResponseDto {
    private UUID id;
    private String name;
    private Date lastUpdated;
    private List<ProductResponseDto> products;
}
