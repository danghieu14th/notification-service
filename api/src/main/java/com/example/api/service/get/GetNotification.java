package com.example.api.service.get;

import com.example.api.service.get.dto.request.GetNotificationAdminDto;
import com.example.api.service.get.dto.request.GetNotificationAdminPageInput;
import com.example.api.service.get.dto.request.GetNotificationCustomerDto;
import com.example.api.service.get.dto.request.GetNotificationCustomerPageInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetNotification {
    Page<GetNotificationAdminDto> getNotificationAdminPage(GetNotificationAdminPageInput input, Pageable pageable);

    Page<GetNotificationCustomerDto> getNotificationCustomerPage(GetNotificationCustomerPageInput input, Pageable pageable);
}
