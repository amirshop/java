package com.ecommerce.backend.dto.account.request;

import lombok.Data;
import java.util.Set;

@Data
public class RoleRequestDto {
    private String label;
    private String value;
    // List of permission IDs to be assigned to this role
    private Set<Long> permissionIds;
}
