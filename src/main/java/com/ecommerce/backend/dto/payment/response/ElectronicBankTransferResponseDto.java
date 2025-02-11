package com.ecommerce.backend.dto.payment.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectronicBankTransferResponseDto extends PaymentResponseDto {
    private String bankAccount;
    private String routingNumber;
}
