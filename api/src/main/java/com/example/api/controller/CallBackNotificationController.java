package com.example.api.controller;

import com.example.api.service.CallBackNotificationService;
import com.example.api.service.NotificationService;
import com.example.api.service.UserService;
import com.example.shared.database.repository.ProviderIntegrationRepository;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/callback")
@RequiredArgsConstructor
@Slf4j
public class CallBackNotificationController {

    private final CallBackNotificationService callBackNotificationService;

    private final ProviderIntegrationRepository providerIntegrationRepository;
    @PostMapping(value ="/twilio", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> getStatus(@RequestBody MultiValueMap<String, String> request){
            String messagingServiceSid = request.getFirst("MessagingServiceSid");
            String messageStatus = request.getFirst("MessageStatus");
            String smsStatus = request.getFirst("SmsStatus");
            String messageSid = request.getFirst("MessageSid");
            if(smsStatus == null || smsStatus.isEmpty())
                smsStatus = "";
            callBackNotificationService.updateStatus(messageSid, smsStatus);
        MessagingResponse twimlResponse = new MessagingResponse.Builder()
                .message(new Message.Builder()
                        .build())
                .build();

        // Return the TwiML response
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/xml")
                .body(twimlResponse.toXml());
    }

}
