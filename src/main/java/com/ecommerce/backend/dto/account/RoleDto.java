package com.ecommerce.backend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class RoleDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String label;
    private String value;
    private Set<PermissionDto> permissions;
}

