package com.example.api.service.send.provider.gmail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GmailProviderIntegrationConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private String auth;
    private String trust;
    private String starttls;
}
