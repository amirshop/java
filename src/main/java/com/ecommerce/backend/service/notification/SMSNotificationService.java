package com.ecommerce.backend.service.notification;

import com.ecommerce.backend.entity.notification.SMSNotification;
import com.ecommerce.backend.repository.notification.SMSNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SMSNotificationService {
    private final SMSNotificationRepository smsNotificationRepository;
    public List<SMSNotification> getAllSMSNotifications() { return smsNotificationRepository.findAll(); }
}
