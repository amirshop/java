package com.ecommerce.backend.dto.account;

import lombok.Data;

@Data
public class AddressDto {

    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
