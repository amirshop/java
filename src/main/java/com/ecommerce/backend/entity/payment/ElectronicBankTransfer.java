package com.ecommerce.backend.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BANK_TRANSFER")
@Data
public class ElectronicBankTransfer extends Payment {
    private String bankAccount;
    private String routingNumber;
}
