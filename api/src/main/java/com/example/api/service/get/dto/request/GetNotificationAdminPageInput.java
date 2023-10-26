package com.example.api.service.get.dto.request;

import com.example.shared.enumeration.ChannelType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class GetNotificationAdminPageInput {
    private Long userId;

    private String channel;

    private String template;

    private Long providerIntegrationId;

    private Instant createdFrom;

    private Instant createdTo;

    private Instant completedFrom;

    private Instant completedTo;

    private String type;

    private String status;
}
