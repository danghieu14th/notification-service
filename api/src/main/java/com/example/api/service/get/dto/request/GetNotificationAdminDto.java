package com.example.api.service.get.dto.request;

import com.example.shared.database.dto.NotificationDTO;
import com.example.shared.enumeration.MessageStatus;
import com.example.shared.enumeration.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class GetNotificationAdminDto {
    private long id;
    private long userId;
    private String title;
    private String body;
    private MessageStatus status;
    private String channel;
    private Long providerIntegrationId;
    private String providerIntegrationName;

    private NotificationType type;

    private Instant created_at;
    private Instant completed_at;

    public static GetNotificationAdminDto fromNotificationDTO(NotificationDTO notificationDTO){
        return GetNotificationAdminDto.builder()
                .id(notificationDTO.getId())
                .userId(notificationDTO.getUserId())
                .title(notificationDTO.getTitle())
                .body(notificationDTO.getBody())
                .status(MessageStatus.valueOf(notificationDTO.getStatus()))
                .channel(notificationDTO.getChannel())
                .providerIntegrationId(notificationDTO.getProviderIntegrationId())
                .providerIntegrationName(notificationDTO.getProviderIntegrationName())
                .type(NotificationType.valueOf(notificationDTO.getType()))
                .created_at(notificationDTO.getCreatedAt())
                .completed_at(notificationDTO.getCompletedAt())
                .build();
    }
}
