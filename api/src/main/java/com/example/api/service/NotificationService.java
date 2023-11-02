package com.example.api.service;

import com.example.api.controller.dto.request.NotificationData;
import com.example.api.controller.dto.request.UserData;
import com.example.api.controller.dto.response.GetNotificationAdminResponse;
import com.example.api.controller.dto.response.GetNotificationCustomerResponse;
import com.example.api.service.get.GetNotification;
import com.example.api.service.get.dto.request.GetNotificationAdminPageInput;
import com.example.api.service.get.dto.request.GetNotificationCustomerPageInput;
import com.example.api.service.send.dto.DataSendNotification;
import com.example.api.service.send.dto.SendNotificationInput;
import com.example.api.service.send.provider.ProviderSendService;
import com.example.shared.database.entity.*;
import com.example.shared.database.repository.*;
import com.example.shared.enumeration.MessageStatus;
import com.example.shared.enumeration.NotificationType;
import com.example.shared.exception.ResourceNotFoundException;
import com.example.shared.utils.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;

    private final TemplateRepository templateRepository;
    private final ProviderIntegrationRepository providerIntegrationRepository;

    private final GetNotification getNotification;
    private final NotificationRepository notificationRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final ApplicationContext applicationContext;
    @Qualifier("stringTemplateEngine")
    private final TemplateEngine templateEngine;

    public void send(SendNotificationInput request) {
        Template template = templateRepository.findByName(request.getTemplate());
        if (template == null) {
            throw new ResourceNotFoundException("Template", "name", request.getTemplate());
//            throw new GimoException(null, ErrorCodeList.RESOURCE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Optional<ProviderIntegration> providerIntegrationOptional
                = providerIntegrationRepository.findById(template.getProviderIntegration().getId());
        providerIntegrationOptional
                .orElseThrow(
                        () -> new ResourceNotFoundException("ProviderIntegration", "id", template.getProviderIntegration().getId().toString()));
        ProviderIntegration providerIntegration = providerIntegrationOptional.get();

        String providerName = providerIntegration.getProviderName();
        ProviderSendService providerSendService = applicationContext.getBean(providerName, ProviderSendService.class);

        String config = providerIntegration.getConfig();

        List<UserData> userData = request.getTo();
        String body = template.getBody();
        String title = template.getTitle();
        Map<Notification, DataSendNotification> dataSendNotifications = new HashMap<>();
        for(UserData data : userData){
            Optional<User> userOptional = userRepository.findById(data.getUserId());
            if (userOptional.isEmpty()) {
                throw new ResourceNotFoundException("User", "id", data.getUserId().toString());
            }
            User user = userOptional.get();
            String titleConvert = convertTemplate(data, title);
            String bodyConvert = convertTemplate(data, body);
            Notification notification = saveNotificationBeforeSending(request, data,titleConvert,bodyConvert,template, user);
            DataSendNotification dataSend = DataSendNotification.builder()
                    .title(titleConvert)
                    .body(bodyConvert)
                    .phoneNumber(user.getPhoneNumber())
                    .notificationId(notification.getId())
                    .email(user.getEmail())
                    .build();
            dataSendNotifications.put(notification,dataSend);
        }
        for (Map.Entry<Notification, DataSendNotification> entry : dataSendNotifications.entrySet()) {
            Notification notification = entry.getKey();
            DataSendNotification dataSend = entry.getValue();
            //send
            providerSendService.send(config, dataSend);

            saveNotificationAfterSent(notification, providerIntegration);
        }
    }

    private void saveNotificationAfterSent(Notification notification, ProviderIntegration providerIntegration) {
        Instant completedAt = Instant.now();

        notification.setStatus(MessageStatus.DELIVERED);
        notification.setCompletedAt(completedAt);
        notificationRepository.save(notification);

        NotificationHistory notificationHistoryAfter = NotificationHistory.builder()
                .notification(notification)
                .status(MessageStatus.DELIVERED)
                .completedAt(completedAt)
                .message("Success")
                .providerIntegrationId(providerIntegration.getId())
                .build();
        notificationHistoryRepository.save(notificationHistoryAfter);
    }

    private Notification saveNotificationBeforeSending(SendNotificationInput request, UserData data,String title, String body, Template template, User user) {
        //save notification before send
        Notification notification = Notification.builder()
                .arguments(JsonMapper.writeValueAsString(data))
                .status(MessageStatus.PENDING)
                .title(title)
                .body(body)
                .serviceSource(request.getServiceSource())
                .template(template)
                .type(NotificationType.REMINDER)
                .user(user)
                .build();
        notification = notificationRepository.save(notification);
        NotificationHistory notificationHistory = NotificationHistory.builder()
                .notification(notification)
                .status(MessageStatus.PENDING)
                .build();
        notificationHistoryRepository.save(notificationHistory);
        return notification;
    }

    private String getTitle(UserData data, String title) {
        if (title == null) {
            return null;
        }
        Context context = new Context();
        //set data for template
        for (NotificationData notificationData : data.getData()) {
            String key = notificationData.getKey();
            String pattern1 = "${" + key + "}";
            String pattern2 = "[[\\$\\{" + key + "\\}]]";
            if (title.contains(pattern1) || title.contains(pattern2)) {
                context.setVariable(key, notificationData.getValue());
            }
        }
        //convert template
        return templateEngine.process(title, context);
    }

    private String convertTemplate(UserData data, String str) {
        Context context = new Context();
        List<String> variables = extractVariables(str);
        Map<String, String> params = new HashMap<>();
        //set data for template
        for (NotificationData notificationData : data.getData()) {
            String key = notificationData.getKey();
            params.put(key, notificationData.getValue());
        }
        for (String variable : variables) {
            if (params.containsKey(variable)) {
                context.setVariable(variable, params.get(variable));
            } else {
                throw new ResourceNotFoundException("Template Key", "key", variable);
            }
        }
        return templateEngine.process(str, context);
    }
    public static List<String> extractVariables(String str) {
        // Create a regular expression to match variables surrounded by [[${}]] or ${}.
        String regex = "\\[\\[\\$\\{(.+?)\\}\\]\\]|\\$\\{(.+?)\\}";

        // Create a Matcher object to find all matches of the regular expression in the string.
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<String> variables = new ArrayList<>();

        // For each match found, extract the variable name and add it to the list.
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                variables.add(matcher.group(1));
            } else {
                variables.add(matcher.group(2));
            }
        }

        return variables;
    }
    public GetNotificationAdminResponse getNotificationAdminPage(GetNotificationAdminPageInput input, Pageable pageable) {

        return GetNotificationAdminResponse.from(getNotification.getNotificationAdminPage(input, pageable));
    }

    public GetNotificationCustomerResponse getNotificationCustomerPage(GetNotificationCustomerPageInput input, Pageable pageable) {

        return GetNotificationCustomerResponse.from(getNotification.getNotificationCustomerPage(input, pageable));
    }
}
