package com.ecommerce.backend.service.payment;

import com.ecommerce.backend.dto.payment.request.PaymentRequestDto;
import com.ecommerce.backend.dto.payment.response.PaymentResponseDto;
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

    public List<PaymentResponseDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentResponseDto.class))
                .collect(Collectors.toList());
    }

    public PaymentResponseDto getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> modelMapper.map(payment, PaymentResponseDto.class))
                .orElse(null);
    }

    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequest) {
        Payment payment = modelMapper.map(paymentRequest, Payment.class);
        payment.setStatus(PaymentStatus.COMPLETED); // Example: Mark as completed
        Payment saved = paymentRepository.save(payment);
        return modelMapper.map(saved, PaymentResponseDto.class);
    }

    public PaymentResponseDto updatePayment(UUID paymentId, PaymentRequestDto paymentRequest) {
        return paymentRepository.findById(paymentId)
                .map(existing -> {
                    existing.setAmount(paymentRequest.getAmount());
                    existing.setPaymentDate(paymentRequest.getPaymentDate());
                    existing.setStatus(paymentRequest.getStatus());
                    Payment updated = paymentRepository.save(existing);
                    return modelMapper.map(updated, PaymentResponseDto.class);
                })
                .orElse(null);
    }

    public void deletePayment(UUID paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}

