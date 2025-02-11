package com.ecommerce.backend.dto.payment.request;

import com.ecommerce.backend.dto.account.AddressDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCardTransactionRequestDto extends PaymentRequestDto {
    private String cardNumber;
    private String nameOnCard;
    private AddressDto billingAddress;
}

