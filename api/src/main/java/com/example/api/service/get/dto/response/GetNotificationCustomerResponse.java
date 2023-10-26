package com.example.api.service.get.dto.response;

import com.example.api.service.get.dto.request.GetNotificationAdminDto;
import com.example.api.service.get.dto.request.GetNotificationCustomerDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class GetNotificationCustomerResponse {
    List<GetNotificationCustomerDto> content;

    Integer totalPages;

    Long totalElements;

    Integer size;

    public static GetNotificationCustomerResponse from(Page<GetNotificationCustomerDto> page) {
        return GetNotificationCustomerResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .build();
    }
}
