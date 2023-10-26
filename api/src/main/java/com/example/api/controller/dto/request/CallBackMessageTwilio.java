package com.example.api.controller.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
public class CallBackMessageTwilio {
    private String messageSid;
    private String messageStatus;
    private String to;
    private String from;
    private String errorCode;
    private String errorMessage;
}
