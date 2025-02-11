package com.ecommerce.backend.dto.payment.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectronicBankTransferRequestDto extends PaymentRequestDto {
    private String bankAccount;
    private String routingNumber;
}

