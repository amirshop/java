package com.ecommerce.backend.entity.notification;

import com.ecommerce.backend.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;
    private String title;
    private String message;

    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
