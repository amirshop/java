package com.ecommerce.backend.dto.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SMSNotificationDto extends NotificationDto {
    private String phoneNumber;
}

