package com.example.api.service.send.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataSendNotification {
    Long notificationId;
    String title;
    String body;
    String phoneNumber;
    String email;
}
