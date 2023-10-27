package com.example.api.controller.dto.request;

import com.example.api.service.get.dto.request.GetNotificationCustomerPageInput;
import com.example.shared.enumeration.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.Instant;

@Data
@Builder
public class GetNotificationCustomerRequest {

    private Long userId;
    @EnumValidator(enumClass = ChannelType.class)
    private ChannelType channel;

    @PastOrPresent(message = "CreatedFrom must be in the past or present")
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

    private String sortBy;
    @EnumValidator(enumClass = SortDirection.class)
    private SortDirection sortDirection;

    public GetNotificationCustomerPageInput toInput(){
        return GetNotificationCustomerPageInput.builder()
                .userId(userId)
                .channel(channel == null ? null : channel.getValue())
                .completedFrom(completedFrom)
                .completedTo(completedTo)
                .type(type == null ? null : type.getValue())
                .status(status == null ? null : status.getValue())
                .build();
    }
}
