package com.ecommerce.backend.repository.payment;

import com.ecommerce.backend.entity.payment.ElectronicBankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectronicBankTransferRepository extends JpaRepository<ElectronicBankTransfer, UUID> {
}
