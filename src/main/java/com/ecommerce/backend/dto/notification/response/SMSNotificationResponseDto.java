package com.ecommerce.backend.dto.notification.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SMSNotificationResponseDto extends NotificationResponseDto {
    private String phoneNumber;
}

