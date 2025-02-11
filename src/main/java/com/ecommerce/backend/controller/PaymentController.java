package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.payment.request.PaymentRequestDto;
import com.ecommerce.backend.dto.payment.response.PaymentResponseDto;
import com.ecommerce.backend.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        List<PaymentResponseDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponseDto payment = paymentService.getPaymentById(paymentId);
        return payment != null
                ? ResponseEntity.ok(payment)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody PaymentRequestDto paymentRequest) {
        PaymentResponseDto processedPayment = paymentService.processPayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(processedPayment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@PathVariable Long paymentId,
                                                            @RequestBody PaymentRequestDto paymentRequest) {
        PaymentResponseDto updatedPayment = paymentService.updatePayment(paymentId, paymentRequest);
        return updatedPayment != null
                ? ResponseEntity.ok(updatedPayment)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
