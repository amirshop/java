package com.ecommerce.backend.service.notification;

import com.ecommerce.backend.dto.notification.NotificationDto;
import com.ecommerce.backend.entity.notification.Notification;
import com.ecommerce.backend.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final ModelMapper modelMapper;

    public List<NotificationDto> getNotificationsForAccount(UUID accountId) {
        List<Notification> notifications = notificationRepository.findByAccountId(accountId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }

    public NotificationDto getNotificationById(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .orElse(null);
    }

    public NotificationDto markAsRead(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> {
                    notification.setRead(true);
                    Notification updated = notificationRepository.save(notification);
                    return modelMapper.map(updated, NotificationDto.class);
                })
                .orElse(null);
    }
}
