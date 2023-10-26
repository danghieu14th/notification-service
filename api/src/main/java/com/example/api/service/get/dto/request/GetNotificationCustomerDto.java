package com.example.api.service.get.dto.request;

import com.example.shared.database.dto.NotificationDTO;
import com.example.shared.enumeration.MessageStatus;
import com.example.shared.enumeration.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class GetNotificationCustomerDto {
    private long id;
    private long userId;
    private String title;
    private String body;
    private MessageStatus status;
    private String channel;
    private NotificationType type;

    private Instant completed_at;

    public static GetNotificationCustomerDto fromNotificationDTO(NotificationDTO notificationDTO){
        return GetNotificationCustomerDto.builder()
                .id(notificationDTO.getId())
                .userId(notificationDTO.getUserId())
                .title(notificationDTO.getTitle())
                .body(notificationDTO.getBody())
                .status(MessageStatus.valueOf(notificationDTO.getStatus()))
                .channel(notificationDTO.getChannel())
                .type(NotificationType.valueOf(notificationDTO.getType()))
                .completed_at(notificationDTO.getCompletedAt())
                .build();
    }
}
