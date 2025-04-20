package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Data
public class BrandDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @AllowedFilter
    private String name;

    @AllowedFilter
    private String country;

    private String description;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
