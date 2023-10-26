package com.example.api.service;

import com.example.api.controller.dto.request.NotificationData;
import com.example.api.controller.dto.request.UserData;
import com.example.api.service.get.GetNotification;
import com.example.api.service.get.dto.request.GetNotificationAdminPageInput;
import com.example.api.service.get.dto.request.GetNotificationCustomerPageInput;
import com.example.api.service.get.dto.response.GetNotificationAdminResponse;
import com.example.api.service.get.dto.response.GetNotificationCustomerResponse;
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
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    private final TemplateEngine templateEngine;

    public void send(SendNotificationInput request){
        Template template = templateRepository.findByName(request.getTemplate());
        if (template == null) {
            throw new ResourceNotFoundException("Template", "name", request.getTemplate());
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
        Context context = new Context();
        for (UserData data : userData){

            Optional<User> userOptional = userRepository.findById(data.getUserId());
            if(userOptional.isEmpty()){
                throw new ResourceNotFoundException("User", "id", data.getUserId().toString());
            }
            User user = userOptional.get();

            //set data for template
            for(NotificationData notificationData : data.getData()){
                context.setVariable(notificationData.getKey(), notificationData.getValue());
            }
            //convert template
            body = templateEngine.process(body, context);

            //save notification before send
            Notification notification = Notification.builder()
                    .arguments(JsonMapper.writeValueAsString(data))
                    .status(MessageStatus.PENDING)
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

            //create data send
            DataSendNotification dataSend = DataSendNotification.builder()
                    .notificationId(notification.getId())
                    .title(template.getTitle())
                    .body(body)
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .build();

            //send
            providerSendService.send(config, dataSend);

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
    }
    public GetNotificationAdminResponse getNotificationAdminPage(GetNotificationAdminPageInput input, Pageable pageable) {

        return GetNotificationAdminResponse.from(getNotification.getNotificationAdminPage(input, pageable));
    }

    public GetNotificationCustomerResponse getNotificationCustomerPage(GetNotificationCustomerPageInput input, Pageable pageable) {

        return GetNotificationCustomerResponse.from(getNotification.getNotificationCustomerPage(input, pageable));
    }
}
