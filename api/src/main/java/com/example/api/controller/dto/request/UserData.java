package com.example.api.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserData {
    @JsonProperty("user_id")
    @NotNull
    private Long userId;

    @JsonProperty("data")
    @NotNull
    @Valid
    private List<NotificationData> data;
}
