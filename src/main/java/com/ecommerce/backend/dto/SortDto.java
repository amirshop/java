package com.ecommerce.backend.dto;

import lombok.Data;

@Data
public class SortDto {

    private String field;      // نام ستون مرتب‌سازی
    private String direction;  // "asc" یا "desc"
}
