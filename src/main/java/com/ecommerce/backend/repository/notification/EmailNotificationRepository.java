package com.ecommerce.backend.repository.notification;

import com.ecommerce.backend.entity.notification.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
}