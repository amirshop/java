package com.ecommerce.backend.dto.customer;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.dto.payment.PaymentDto;
import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.customer.CustomerAddress;
import com.ecommerce.backend.entity.customer.CustomerProfile;
import com.ecommerce.backend.entity.customer.WishlistItem;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.entity.payment.Payment;
import com.ecommerce.backend.entity.product.ProductReview;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CustomerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;

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
            description = "phone number of the UserAccount", example = "9198881400"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "phone number must be 10 digits")
    @AllowedFilter
    private String phone;

    private String firstName;
    private String lastName;

    private CustomerProfile profile;

    private List<CustomerAddress> addresses;

    private List<PaymentDto> paymentMethods;

    private List<OrderDto> orders;

    private List<WishlistItem> wishlist;

    private List<ProductReview> accountReviews;

    private Cart cart;
}
