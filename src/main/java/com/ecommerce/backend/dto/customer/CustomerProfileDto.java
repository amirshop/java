package com.ecommerce.backend.dto.customer;

import com.ecommerce.backend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CustomerProfileDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    private UUID customerId;

    private Gender gender;

    private Date birthDate;

    private String profilePictureUrl;
}
