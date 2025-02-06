package com.ecommerce.backend.entity.payment;

import com.ecommerce.backend.entity.account.Address;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("CREDIT_CARD")
@Data
public class CreditCardTransaction extends Payment {
    private String cardNumber;
    private String nameOnCard;

    @Embedded
    private Address billingAddress;
}
