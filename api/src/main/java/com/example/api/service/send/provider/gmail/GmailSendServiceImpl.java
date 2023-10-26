package com.example.api.service.send.provider.gmail;

import com.example.api.service.send.dto.DataSendNotification;
import com.example.api.service.send.provider.ProviderSendService;
import com.example.shared.exception.ResourceNotFoundException;
import com.example.shared.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service("gmail")
@Slf4j
public class GmailSendServiceImpl implements ProviderSendService {
    @Override
    @Async("taskExecutor")
    public void send(String config, DataSendNotification data) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        GmailProviderIntegrationConfig gmailConfig = JsonMapper.convertJsonToObject(config, GmailProviderIntegrationConfig.class);
        if(gmailConfig == null){
            throw new ResourceNotFoundException("GmailProviderIntegrationConfig", "config", config);
        }
        mailSender.setHost(gmailConfig.getHost());
        mailSender.setPort(gmailConfig.getPort());
        mailSender.setUsername(gmailConfig.getUsername());
        mailSender.setPassword(gmailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", (gmailConfig.getProtocol()));
        props.put("mail.smtp.auth", (gmailConfig.getAuth()));
        props.put("mail.smtp.starttls.enable", gmailConfig.getStarttls());
//        props.put("mail.debug", "true");
//        props.put("mail.smtp.ssl.trust", ((GmailProviderIntegrationConfig) config).getTrust());

        try{
            log.info("Execute method with configured executor" +
                    Thread.currentThread().getName());
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(data.getBody(), true);
            helper.setTo(data.getEmail());
            helper.setSubject(data.getTitle());
            helper.setFrom(gmailConfig.getUsername());
            log.info("Send email to {}, provider {}", data.getEmail(), "gmail");
            mailSender.send(message);
        }catch (MessagingException e){
            log.error("Fail to send email", e);
            throw new IllegalStateException("Fail to send email");
        }
    }
}
