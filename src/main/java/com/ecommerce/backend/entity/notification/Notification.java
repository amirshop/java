package com.ecommerce.backend.entity.notification;

import com.ecommerce.backend.entity.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
