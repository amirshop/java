package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.payment.PaymentDto;
import com.ecommerce.backend.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable UUID paymentId) {
        PaymentDto payment = paymentService.getPaymentById(paymentId);
        return payment != null
                ? ResponseEntity.ok(payment)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PaymentDto> processPayment(@RequestBody PaymentDto paymentRequest) {
        PaymentDto processedPayment = paymentService.processPayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(processedPayment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable UUID paymentId,
                                                            @RequestBody PaymentDto paymentRequest) {
        PaymentDto updatedPayment = paymentService.updatePayment(paymentId, paymentRequest);
        return updatedPayment != null
                ? ResponseEntity.ok(updatedPayment)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}
