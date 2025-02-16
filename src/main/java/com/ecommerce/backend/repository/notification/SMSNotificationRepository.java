package com.ecommerce.backend.repository.notification;

import com.ecommerce.backend.entity.notification.SMSNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SMSNotificationRepository extends JpaRepository<SMSNotification, UUID> {
}
