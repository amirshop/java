package com.ecommerce.backend.service.notification;

import com.ecommerce.backend.entity.notification.EmailNotification;
import com.ecommerce.backend.repository.notification.EmailNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    private final EmailNotificationRepository emailNotificationRepository;
    public List<EmailNotification> getAllEmailNotifications() { return emailNotificationRepository.findAll(); }
}
