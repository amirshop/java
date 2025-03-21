package com.ecommerce.backend.repository.notification;

import com.ecommerce.backend.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByCustomerId(UUID accountId);
}
