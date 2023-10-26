package com.example.api.utils;

import com.example.api.controller.dto.request.NotificationData;
import com.example.api.controller.dto.request.UserData;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class TemplateConvert {
    public static String getBody(UserData data, String body) {
        List<NotificationData> notificationData = data.getData();
        for (NotificationData notification : notificationData) {
            String key = notification.getKey();
            String value = notification.getValue();
            body = body.replace("$" + "{" + key + "}", value);
        }

        if (body.contains("${")) {
            throw new InvalidParameterException("Missing data argument");
        }
        return body;
    }
}
