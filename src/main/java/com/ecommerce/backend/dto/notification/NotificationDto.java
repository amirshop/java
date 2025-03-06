package com.ecommerce.backend.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class NotificationDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String title;
    private String message;
    private Date createdAt;
    private boolean isRead;
    private Long accountId;
    // A field to indicate the concrete type (e.g., EMAIL or SMS)
    private String notificationType;
}

