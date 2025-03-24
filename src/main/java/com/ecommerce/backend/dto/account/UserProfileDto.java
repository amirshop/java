package com.ecommerce.backend.dto.account;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.ecommerce.backend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Schema(
        name = "UserProfile",
        description = "Schema to hold UserProfile information"
)
public class UserProfileDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    @AllowedFilter
    private UUID userId;

    @Schema(description = "firstname of the UserProfile", example = "امیر"
    )
    @NotEmpty(message = "firstname can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the firstname should be between 3 and 50")
    @AllowedFilter
    private String firstname;

    @Schema(
            description = "firstname of the UserProfile", example = "مقامی"
    )
    @NotEmpty(message = "firstname can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the firstname should be between 3 and 50")
    @AllowedFilter
    private String lastname;

    @AllowedFilter
    private Gender gender;

    @NotNull
    @Valid
    private AddressDto address;

    private String profilePictureUrl;
}
