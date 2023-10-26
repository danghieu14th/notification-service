package com.example.api.service.send.provider.twilio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming
public class TwilioProviderIntegrationConfig {
    @JsonProperty("account_sid")
    private String accountSid;
    @JsonProperty("auth_token")
    private String authToken;
    @JsonProperty("from_number")
    private String fromNumber;
    @JsonProperty("messaging_service_sid")
    private String messagingServiceSid;
}
