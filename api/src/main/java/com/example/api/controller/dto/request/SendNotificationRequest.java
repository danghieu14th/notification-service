package com.example.api.controller.dto.request;

import com.example.api.service.send.dto.SendNotificationInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class SendNotificationRequest {

    @NotNull
    @Valid
    private List<UserData> to;
    @NotNull
    @NotBlank
    @JsonProperty("service_source")
    private String serviceSource;
    @NotNull
    @NotBlank
    private String template;

    public SendNotificationInput toInput(){
        return SendNotificationInput.builder()
                .to(this.getTo())
                .serviceSource(this.getServiceSource())
                .template(this.getTemplate())
                .build();
    }
}
