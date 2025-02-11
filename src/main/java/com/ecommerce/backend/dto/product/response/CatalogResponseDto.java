package com.ecommerce.backend.dto.product.response;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CatalogResponseDto {
    private Long id;
    private String name;
    private Date lastUpdated;
    private List<ProductResponseDto> products;
}
