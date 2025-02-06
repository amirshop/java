package com.ecommerce.backend.repository.payment;

import com.ecommerce.backend.entity.payment.CreditCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardTransactionRepository extends JpaRepository<CreditCardTransaction, Long> {
}
