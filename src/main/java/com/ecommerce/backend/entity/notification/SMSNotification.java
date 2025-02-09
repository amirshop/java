package com.ecommerce.backend.entity.notification;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("SMS")
@Data
public class SMSNotification extends Notification {
    private String phoneNumber;
}
