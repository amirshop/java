package com.ecommerce.backend.repository.payment;

import com.ecommerce.backend.entity.payment.ElectronicBankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectronicBankTransferRepository extends JpaRepository<ElectronicBankTransfer, Long> {
}
