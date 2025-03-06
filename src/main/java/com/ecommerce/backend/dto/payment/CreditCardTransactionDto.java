package com.ecommerce.backend.dto.payment;

import com.ecommerce.backend.dto.account.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCardTransactionDto extends PaymentDto {
    private String cardNumber;
    private String nameOnCard;
    private AddressDto billingAddress;
}

