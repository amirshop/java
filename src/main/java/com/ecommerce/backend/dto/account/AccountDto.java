package com.ecommerce.backend.dto.account;

import com.ecommerce.backend.dto.cart.CartDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String username;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String status;
    private AddressDto shippingAddress;
    private CartDto cart;
    private Set<RoleDto> roles;
}

