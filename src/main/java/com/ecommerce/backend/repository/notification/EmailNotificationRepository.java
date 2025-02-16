package com.ecommerce.backend.repository.notification;

import com.ecommerce.backend.entity.notification.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, UUID> {
}