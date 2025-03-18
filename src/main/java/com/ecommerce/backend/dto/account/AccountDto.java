package com.ecommerce.backend.dto.account;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.ecommerce.backend.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @AllowedFilter
    private String username;

    @NotEmpty(message = "password can not be a null or empty")
    @NotNull(message = "password can not be a null or empty")
    @Schema(
            description = "password", example = "password12345"
    )
    private String password;

    @Schema(
            description = "email address of the account", example = "sample@email.com"
    )
    @NotEmpty(message = "email address can not be a null or empty")
    @Email(message = "email address should be a valid value")
    @AllowedFilter
    private String email;

    @Schema(
            description = "phone number of the customer", example = "9198881400"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "phone number must be 10 digits")
    @AllowedFilter
    private String phone;

    @Schema(
            description = "firstname of the account", example = "امیر"
    )
    @NotEmpty(message = "firstname can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the firstname should be between 3 and 30")
    @AllowedFilter
    private String firstname;

    @Schema(
            description = "firstname of the account", example = "مقامی"
    )
    @NotEmpty(message = "firstname can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the firstname should be between 3 and 30")
    @AllowedFilter
    private String lastname;

    @AllowedFilter
    private AccountStatus status;

    @NotNull
    @Valid
    private AddressDto address;

//    private CartDto cart;

    @Schema(
            description = "roles of the accounts", example = "ROLE_ADMIN"
    )
    @NotEmpty(message = "role can not be a null or empty")
    private Set<UUID> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
