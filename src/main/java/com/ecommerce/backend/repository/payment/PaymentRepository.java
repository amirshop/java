package com.ecommerce.backend.repository.payment;

import com.ecommerce.backend.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
