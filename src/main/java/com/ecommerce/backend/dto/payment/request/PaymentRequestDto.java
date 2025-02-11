package com.ecommerce.backend.dto.payment.request;

import com.ecommerce.backend.enums.PaymentStatus;
import lombok.Data;
import java.util.Date;

@Data
public class PaymentRequestDto {
    private double amount;
    private PaymentStatus status; // e.g., UNPAID, COMPLETED, etc.
    private Date paymentDate;
}

