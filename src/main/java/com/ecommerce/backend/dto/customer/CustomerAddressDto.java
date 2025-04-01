package com.ecommerce.backend.dto.customer;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Schema(
        name = "Address",
        description = "Schema to hold Address information"
)
public class CustomerAddressDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

    @AllowedFilter
    private UUID customerId;

    @Schema(
            description = "street of the customer", example = "خیابان ..."
    )
    @NotEmpty(message = "street can not be a null or empty")
    @Size(min = 3, max = 255, message = "The length of the street should be between 3 and 255")
    @AllowedFilter
    private String street;

    @Schema(
            description = "city of the customer", example = "تهران"
    )
    @NotEmpty(message = "city can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the city should be between 3 and 50")
    @AllowedFilter
    private String city;

    @Schema(
            description = "state of the customer", example = "تهران"
    )
    @NotEmpty(message = "state can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the state should be between 3 and 50")
    @AllowedFilter
    private String state;

    @Schema(
            description = "postalCode of the customer", example = "034849684684"
    )
    @NotEmpty(message = "postalCode can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "phone number must be 10 digits")
    @AllowedFilter
    private String postalCode;

    @Schema(
            description = "country of the customer", example = "ایران"
    )
    @NotEmpty(message = "country can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the country should be between 3 and 50")
    @AllowedFilter
    private String country;

    @Schema(
            description = "phone number of the customer", example = "9198881400"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "phone number must be 10 digits")
    @AllowedFilter
    private String phone;

    @AllowedFilter
    private Boolean isDefault;
}
