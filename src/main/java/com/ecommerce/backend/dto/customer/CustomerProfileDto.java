package com.ecommerce.backend.dto.customer;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.ecommerce.backend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CustomerProfileDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @AllowedFilter
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    private UUID customerId;

    @AllowedFilter
    private Gender gender;

    @AllowedFilter
    private Date birthDate;

    @AllowedFilter
    private String profilePictureUrl;
}
