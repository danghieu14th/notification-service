package com.example.api.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NotificationData {
    @NotNull
    @NotBlank
    private String key;
    @NotNull
    private String value;
}
