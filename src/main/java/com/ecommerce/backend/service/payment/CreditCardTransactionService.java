package com.ecommerce.backend.service.payment;

import com.ecommerce.backend.entity.payment.CreditCardTransaction;
import com.ecommerce.backend.repository.payment.CreditCardTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardTransactionService {
    private final CreditCardTransactionRepository creditCardTransactionRepository;
    public List<CreditCardTransaction> getAllTransactions() { return creditCardTransactionRepository.findAll(); }
}
