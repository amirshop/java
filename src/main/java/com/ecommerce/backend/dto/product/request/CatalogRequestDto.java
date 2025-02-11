package com.ecommerce.backend.dto.product.request;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CatalogRequestDto {
    private String name;
    private Date lastUpdated;
    // Optionally, a list of product IDs to include in the catalog
    private List<Long> productIds;
}

