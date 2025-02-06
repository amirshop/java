package com.ecommerce.backend.repository.notification;

import com.ecommerce.backend.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
