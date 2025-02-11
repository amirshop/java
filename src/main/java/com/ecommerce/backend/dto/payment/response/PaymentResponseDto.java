package com.ecommerce.backend.dto.payment.response;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentResponseDto {
    private Long id;
    private double amount;
    private String status;
    private Date paymentDate;
    // Indicates the payment type (for example, BANK_TRANSFER or CREDIT_CARD)
    private String paymentType;
}
