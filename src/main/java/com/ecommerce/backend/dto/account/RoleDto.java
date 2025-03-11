package com.ecommerce.backend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class RoleDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Schema(
            description = "label of the role", example = "ادمین"
    )
    @NotEmpty(message = "label can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the label should be between 3 and 50")
    private String label;

    @Schema(
            description = "value of the role", example = "ROLE_ADMIN"
    )
    @NotEmpty(message = "value can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the value should be between 3 and 50")
    private String value;

    private Set<UUID> permissions;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}

