package com.ecommerce.backend.dto.payment.request;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentRequestDto {
    private double amount;
    private String status; // e.g., UNPAID, COMPLETED, etc.
    private Date paymentDate;
}

