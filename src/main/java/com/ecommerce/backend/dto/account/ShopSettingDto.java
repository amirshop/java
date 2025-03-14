package com.ecommerce.backend.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ShopSettingDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;
    private String slug;
    private String description;
    private String logo;
    private String favicon;
    private String currency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    private UUID accountId;
}
