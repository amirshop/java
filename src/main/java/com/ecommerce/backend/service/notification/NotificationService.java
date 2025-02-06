package com.ecommerce.backend.service.notification;

import com.ecommerce.backend.entity.notification.Notification;
import com.ecommerce.backend.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public List<Notification> getAllNotifications() { return notificationRepository.findAll(); }
}
