package com.ecommerce.backend.dto.account.response;

import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
public class RoleResponseDto {
    private UUID id;
    private String label;
    private String value;
    private Set<PermissionResponseDto> permissions;
}

