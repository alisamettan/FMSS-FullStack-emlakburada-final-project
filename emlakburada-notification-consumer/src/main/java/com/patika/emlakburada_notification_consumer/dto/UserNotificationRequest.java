package com.patika.emlakburada_notification_consumer.dto;

import com.patika.emlakburada_notification_consumer.dto.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotificationRequest {
    private String fullName;
    private String email;
    private Integer listingRights;
    private NotificationType notificationType;
}
