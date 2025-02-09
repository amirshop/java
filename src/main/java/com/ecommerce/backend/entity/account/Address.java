package com.ecommerce.backend.entity.account;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}