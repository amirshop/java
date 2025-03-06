package com.ecommerce.backend.dto.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectronicBankTransferDto extends PaymentDto {
    private String bankAccount;
    private String routingNumber;
}
