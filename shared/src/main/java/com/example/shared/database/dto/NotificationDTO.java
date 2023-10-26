package com.example.shared.database.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public interface NotificationDTO {
    Long getId();

    Long getUserId();
    String getTitle();
    String getBody();
    Instant getCreatedAt();
    Instant getCompletedAt();
    String getType();
    String getStatus();
    String getTemplate();
    String getChannel();
    Long getProviderIntegrationId();
    String getProviderIntegrationName();
}
