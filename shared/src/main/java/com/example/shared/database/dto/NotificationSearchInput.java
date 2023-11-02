package com.example.shared.database.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class NotificationSearchInput {
    private Long userId;
    private String template;
    private String channel;
    private Instant createdFrom;
    private Instant createdTo;
    private Instant completedFrom;
    private Instant completedTo;
    private String type;
    private String status;
}
