package com.ecommerce.backend.dto.notification.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmailNotificationResponseDto extends NotificationResponseDto {
    private String email;
}
