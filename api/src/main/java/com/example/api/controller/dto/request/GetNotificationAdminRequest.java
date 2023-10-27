package com.example.api.controller.dto.request;

import com.example.api.service.get.dto.request.GetNotificationAdminPageInput;
import com.example.shared.enumeration.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Builder
public class GetNotificationAdminRequest {

    private Long userId;
    @EnumValidator(enumClass = ChannelType.class)
    private ChannelType channel;

    private Long provider;
    @PastOrPresent(message = "CreatedFrom must be in the past or present")
    private Instant createdFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant createdTo;

    @PastOrPresent(message = "CompletedFrom must be in the past or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant completedFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant completedTo;

    @EnumValidator(enumClass = NotificationType.class)
    private NotificationType type;

    @EnumValidator(enumClass = MessageStatus.class)
    private MessageStatus status;

    @Min(value = 0, message = "Page must be greater than or equal to 0")
    private Integer page;

    @Min(value = 0, message = "Page size must be greater than or equal to 0")
    @Max(value = 100, message = "Page size must be less than or equal to 100")
    private Integer size;

//    @EnumValidator(enumClass = SortBy.class)
//    private SortBy sortBy;
    private String sortBy;
    @EnumValidator(enumClass = SortDirection.class)
    private SortDirection sortDirection;

    public GetNotificationAdminPageInput toInput(){
        return GetNotificationAdminPageInput.builder()
                .userId(userId)
                .channel(channel == null ? null : channel.getValue())
                .providerIntegrationId(provider)
                .createdFrom(createdFrom)
                .createdTo(createdTo)
                .completedFrom(completedFrom)
                .completedTo(completedTo)
                .type(type == null ? null : type.getValue())
                .status(status ==null ? null : status.getValue())
                .build();
    }
}
