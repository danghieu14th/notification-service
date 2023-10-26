package com.example.api.service.get;

import com.example.api.service.get.dto.request.GetNotificationAdminDto;
import com.example.api.service.get.dto.request.GetNotificationAdminPageInput;
import com.example.api.service.get.dto.request.GetNotificationCustomerDto;
import com.example.api.service.get.dto.request.GetNotificationCustomerPageInput;
import com.example.shared.database.dto.NotificationDTO;
import com.example.shared.database.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNotificationImpl implements GetNotification {
    private final NotificationRepository notificationRepository;

    @Override
    public Page<GetNotificationAdminDto> getNotificationAdminPage(GetNotificationAdminPageInput input, Pageable pageable) {
        Page<NotificationDTO> notificationPage = notificationRepository.getNotificationDTO(
                input.getUserId(), input.getTemplate(),
                input.getChannel(),
                input.getCreatedFrom(),
                input.getCreatedTo(), input.getCompletedFrom(),
                input.getCompletedTo(),
                input.getType(),
                input.getStatus(),
                pageable);

        return notificationPage.map(GetNotificationAdminDto::fromNotificationDTO);
    }

    @Override
    public Page<GetNotificationCustomerDto> getNotificationCustomerPage(GetNotificationCustomerPageInput input, Pageable pageable) {
        Page<NotificationDTO> notificationPage = notificationRepository.getNotificationDTO(
                input.getUserId(), null,
                input.getChannel(),
                null,
                null, input.getCompletedFrom(),
                input.getCompletedTo(),
                input.getType(),
                input.getStatus(),
                pageable);

        return notificationPage.map(GetNotificationCustomerDto::fromNotificationDTO);
    }

}
