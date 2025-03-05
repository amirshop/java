package com.ecommerce.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchDto {
    private int page = 0;
    private int size = 10;
    private List<FilterCriteria> filters;
    private String sortField;      // نام ستون مرتب‌سازی
    private String sortDirection;  // "asc" یا "desc"
}

