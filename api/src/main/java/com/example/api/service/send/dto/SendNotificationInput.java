package com.example.api.service.send.dto;

import com.example.api.controller.dto.request.UserData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SendNotificationInput {

    @JsonProperty("to")
    private List<UserData> to;

    @JsonProperty("service_source")
    private String serviceSource;

    @JsonProperty("template")
    private String template;

}
