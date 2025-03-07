package com.ecommerce.backend.dto.account;

import com.ecommerce.backend.dto.cart.CartDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String username;

    @NotEmpty(message = "password can not be a null or empty")
    @NotNull(message = "password can not be a null or empty")
    @Schema(
            description = "password", example = "password12345"
    )
    private String password;

    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String status;
    private AddressDto shippingAddress;
    private CartDto cart;
    private Set<RoleDto> roles;
}

