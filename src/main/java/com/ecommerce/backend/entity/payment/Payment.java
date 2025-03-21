package com.ecommerce.backend.entity.payment;

import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
