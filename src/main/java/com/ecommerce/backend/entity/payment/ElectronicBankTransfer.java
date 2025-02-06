package com.ecommerce.backend.entity.payment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("BANK_TRANSFER")
@Data
public class ElectronicBankTransfer extends Payment {
    private String bankAccount;
    private String routingNumber;
}
