package com.ecommerce.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
