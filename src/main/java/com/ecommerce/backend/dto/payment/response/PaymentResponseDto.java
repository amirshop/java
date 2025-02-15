package com.ecommerce.backend.dto.payment.response;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class PaymentResponseDto {
    private UUID id;
    private double amount;
    private String status;
    private Date paymentDate;
    // Indicates the payment type (for example, BANK_TRANSFER or CREDIT_CARD)
    private String paymentType;
}
