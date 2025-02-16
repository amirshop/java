package com.ecommerce.backend.dto.account.response;

import com.ecommerce.backend.dto.account.AddressDto;
import com.ecommerce.backend.dto.cart.response.CartResponseDto;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
public class AccountResponseDto {
    private UUID id;
    private String username;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String status;
    private AddressDto shippingAddress;
    private CartResponseDto cart;
    private Set<RoleResponseDto> roles;
}

