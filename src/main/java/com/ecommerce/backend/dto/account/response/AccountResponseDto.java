package com.ecommerce.backend.dto.account.response;

import com.ecommerce.backend.dto.account.AddressDto;
import com.ecommerce.backend.dto.cart.response.CartResponseDto;
import lombok.Data;
import java.util.Set;

@Data
public class AccountResponseDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String name;
    private String status;
    private AddressDto shippingAddress;
    private CartResponseDto cart;
    private Set<RoleResponseDto> roles;
}

