package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.notification.NotificationDto;
import com.ecommerce.backend.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByAccount(@PathVariable UUID accountId) {
        List<NotificationDto> notifications = notificationService.getNotificationsForAccount(accountId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable UUID notificationId) {
        NotificationDto notification = notificationService.getNotificationById(notificationId);
        return notification != null
                ? ResponseEntity.ok(notification)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDto> markNotificationAsRead(@PathVariable UUID notificationId) {
        NotificationDto updatedNotification = notificationService.markAsRead(notificationId);
        return updatedNotification != null
                ? ResponseEntity.ok(updatedNotification)
                : ResponseEntity.notFound().build();
    }
}
