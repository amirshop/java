package com.ecommerce.backend.dto.notification.response;

import lombok.Data;
import java.util.Date;

@Data
public class NotificationResponseDto {
    private Long id;
    private String title;
    private String message;
    private Date createdAt;
    private boolean isRead;
    private Long accountId;
    // A field to indicate the concrete type (e.g., EMAIL or SMS)
    private String notificationType;
}

