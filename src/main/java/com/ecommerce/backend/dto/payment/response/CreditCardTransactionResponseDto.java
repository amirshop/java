package com.ecommerce.backend.dto.payment.response;

import com.ecommerce.backend.dto.account.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCardTransactionResponseDto extends PaymentResponseDto {
    private String cardNumber;
    private String nameOnCard;
    private AddressDto billingAddress;
}

