package com.ecommerce.backend.service.payment;

import com.ecommerce.backend.entity.payment.ElectronicBankTransfer;
import com.ecommerce.backend.repository.payment.ElectronicBankTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectronicBankTransferService {
    private final ElectronicBankTransferRepository electronicBankTransferRepository;
    public List<ElectronicBankTransfer> getAllTransfers() { return electronicBankTransferRepository.findAll(); }
}
