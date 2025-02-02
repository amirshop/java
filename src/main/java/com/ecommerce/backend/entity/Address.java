package com.ecommerce.backend.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}