package com.ecommerce.backend.dto.notification.response;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class NotificationResponseDto {
    private UUID id;
    private String title;
    private String message;
    private Date createdAt;
    private boolean isRead;
    private Long accountId;
    // A field to indicate the concrete type (e.g., EMAIL or SMS)
    private String notificationType;
}

