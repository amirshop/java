package com.ecommerce.backend.dto.payment;

import com.ecommerce.backend.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private double amount;
    private PaymentStatus status;
    private Date paymentDate;
    // Indicates the payment type (for example, BANK_TRANSFER or CREDIT_CARD)
    private String paymentType;
}
