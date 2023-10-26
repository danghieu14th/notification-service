package com.example.api.service.send.provider;

import com.example.api.service.send.dto.DataSendNotification;

public interface ProviderSendService {
    void send(String config, DataSendNotification data);

}
