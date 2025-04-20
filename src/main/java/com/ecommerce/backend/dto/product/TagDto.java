package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TagDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @AllowedFilter
    private UUID id;

    @AllowedFilter
    private String name;

    @AllowedFilter
    private String slug;

    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}

