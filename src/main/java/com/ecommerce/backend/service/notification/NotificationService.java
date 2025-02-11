package com.ecommerce.backend.service.notification;

import com.ecommerce.backend.dto.notification.response.NotificationResponseDto;
import com.ecommerce.backend.entity.notification.Notification;
import com.ecommerce.backend.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final ModelMapper modelMapper;

    public List<NotificationResponseDto> getNotificationsForAccount(Long accountId) {
        List<Notification> notifications = notificationRepository.findByAccountId(accountId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationResponseDto.class))
                .collect(Collectors.toList());
    }

    public NotificationResponseDto getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> modelMapper.map(notification, NotificationResponseDto.class))
                .orElse(null);
    }

    public NotificationResponseDto markAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notification -> {
                    notification.setRead(true);
                    Notification updated = notificationRepository.save(notification);
                    return modelMapper.map(updated, NotificationResponseDto.class);
                })
                .orElse(null);
    }
}
