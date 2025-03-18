package com.ecommerce.backend.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Address",
        description = "Schema to hold Address information"
)
public class AddressDto {

    @Schema(
            description = "streetAddress of the account", example = "خیابان ..."
    )
    @NotEmpty(message = "streetAddress can not be a null or empty")
    @Size(min = 3, max = 255, message = "The length of the streetAddress should be between 3 and 30")
    private String streetAddress;

    @Schema(
            description = "city of the account", example = "تهران"
    )
    @NotEmpty(message = "city can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the city should be between 3 and 30")
    private String city;

    @Schema(
            description = "state of the account", example = "تهران"
    )
    @NotEmpty(message = "state can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the state should be between 3 and 30")
    private String state;

    @Schema(
            description = "postalCode of the account", example = "034849684684"
    )
    @NotEmpty(message = "postalCode can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "phone number must be 10 digits")
    private String postalCode;

    @Schema(
            description = "country of the account", example = "ایران"
    )
    @NotEmpty(message = "country can not be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the country should be between 3 and 30")
    private String country;
}
