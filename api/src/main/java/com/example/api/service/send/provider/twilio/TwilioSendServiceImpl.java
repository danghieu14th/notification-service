package com.example.api.service.send.provider.twilio;

import com.example.api.service.send.dto.DataSendNotification;
import com.example.api.service.send.provider.ProviderSendService;
import com.example.shared.database.entity.Notification;
import com.example.shared.database.entity.NotificationHistory;
import com.example.shared.database.repository.NotificationHistoryRepository;
import com.example.shared.database.repository.NotificationRepository;
import com.example.shared.enumeration.MessageStatus;
import com.example.shared.exception.ResourceNotFoundException;
import com.example.shared.utils.JsonMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("twilio")
@RequiredArgsConstructor
@Slf4j
public class TwilioSendServiceImpl implements ProviderSendService {
    private final NotificationRepository notificationRepository;
    @Override
    public void send(String config, DataSendNotification data) {
        TwilioProviderIntegrationConfig twilioConfig = JsonMapper.convertJsonToObject(config, TwilioProviderIntegrationConfig.class);
        if(twilioConfig == null){
            throw new ResourceNotFoundException("Provider Config", "provider", "twilio");
        }
        Twilio.init(
                twilioConfig.getAccountSid(),
                twilioConfig.getAuthToken()
        );
        String messageServiceSid = twilioConfig.getMessagingServiceSid();
        if(messageServiceSid != null && !messageServiceSid.isEmpty()){
            MessageCreator messageCreator = Message.creator(
                    new PhoneNumber(data.getPhoneNumber()),
                    messageServiceSid,
                    data.getBody()
            );
            Message message = null;
            try {
                Optional<Notification> notificationOptional = notificationRepository.findById(data.getNotificationId());
                if(notificationOptional.isEmpty()){
                    throw new RuntimeException("Notification not found");
                }
                log.info("Send sms to {}, provider {}", data.getPhoneNumber(), "twilio");
                Notification notification = notificationOptional.get();
                message = messageCreator.create(); //send
                notification.setMessageId(message.getSid());
            } catch (Exception e) {
                log.error("Error when send sms to {}", data.getPhoneNumber());
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

        }
        if(messageServiceSid == null || messageServiceSid.isEmpty() && twilioConfig.getFromNumber() != null){
            Message message = Message.creator(new PhoneNumber(data.getPhoneNumber()),
                    new PhoneNumber(twilioConfig.getFromNumber()), data.getBody()).create();
        }

    }

}
