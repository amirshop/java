package com.ecommerce.backend.dto.account.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PermissionResponseDto {
    private UUID id;
    private String label;
    private String value;
}

