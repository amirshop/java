package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.notification.response.NotificationResponseDto;
import com.ecommerce.backend.entity.notification.Notification;
import com.ecommerce.backend.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<NotificationResponseDto>> getNotificationsByAccount(@PathVariable Long accountId) {
        List<NotificationResponseDto> notifications = notificationService.getNotificationsForAccount(accountId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable Long notificationId) {
        NotificationResponseDto notification = notificationService.getNotificationById(notificationId);
        return notification != null
                ? ResponseEntity.ok(notification)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponseDto> markNotificationAsRead(@PathVariable Long notificationId) {
        NotificationResponseDto updatedNotification = notificationService.markAsRead(notificationId);
        return updatedNotification != null
                ? ResponseEntity.ok(updatedNotification)
                : ResponseEntity.notFound().build();
    }
}
