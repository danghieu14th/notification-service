package com.example.api.service.get.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class GetNotificationCustomerPageInput {
    private Long userId;

    private String channel;

    private Long providerIntegrationId;

    private Instant completedFrom;

    private Instant completedTo;

    private String type;

    private String status;
}
