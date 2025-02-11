package com.ecommerce.backend.dto.account.request;

import com.ecommerce.backend.dto.account.AddressDto;
import lombok.Data;

@Data
public class AccountRequestDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    // You can either pass the enum as a String or change the type to your AccountStatus enum.
    private String status;
    private AddressDto shippingAddress;
}
