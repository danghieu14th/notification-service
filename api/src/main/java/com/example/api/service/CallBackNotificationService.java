package com.example.api.service;


import com.example.shared.database.entity.Notification;
import com.example.shared.database.entity.NotificationHistory;
import com.example.shared.database.repository.NotificationHistoryRepository;
import com.example.shared.database.repository.NotificationRepository;
import com.example.shared.enumeration.MessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallBackNotificationService {
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationRepository notificationRepository;

    public void updateStatus(String messageId, String status) {
        Notification notification = notificationRepository.findByMessageId(messageId);
        if(notification == null)
            return;
        notification.setStatus(MessageStatus.valueOf(status.toUpperCase()));
        notificationRepository.save(notification);

        NotificationHistory notificationHistory = NotificationHistory.builder()
                .notification(notification)
                .status(MessageStatus.valueOf(status.toUpperCase()))
                .build();
        notificationHistoryRepository.save(notificationHistory);
    }

}
