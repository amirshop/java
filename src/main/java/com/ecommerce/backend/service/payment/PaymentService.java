package com.ecommerce.backend.service.payment;

import com.ecommerce.backend.dto.payment.PaymentDto;
import com.ecommerce.backend.entity.payment.Payment;
import com.ecommerce.backend.enums.PaymentStatus;
import com.ecommerce.backend.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final ModelMapper modelMapper;

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .orElse(null);
    }

    public PaymentDto processPayment(PaymentDto paymentRequest) {
        Payment payment = modelMapper.map(paymentRequest, Payment.class);
        payment.setStatus(PaymentStatus.COMPLETED); // Example: Mark as completed
        Payment saved = paymentRepository.save(payment);
        return modelMapper.map(saved, PaymentDto.class);
    }

    public PaymentDto updatePayment(UUID paymentId, PaymentDto paymentRequest) {
        return paymentRepository.findById(paymentId)
                .map(existing -> {
                    existing.setAmount(paymentRequest.getAmount());
                    existing.setPaymentDate(paymentRequest.getPaymentDate());
                    existing.setStatus(paymentRequest.getStatus());
                    Payment updated = paymentRepository.save(existing);
                    return modelMapper.map(updated, PaymentDto.class);
                })
                .orElse(null);
    }

    public void deletePayment(UUID paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}

