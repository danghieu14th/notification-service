package com.example.api.controller.response;

import com.example.api.service.get.dto.request.GetNotificationAdminDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Data
public class GetNotificationAdminPageResponse {
    List<GetNotificationAdminDto> content;

    Integer totalPages;
    Long totalElements;
    Integer size;

    public static GetNotificationAdminPageResponse from(Page<GetNotificationAdminDto> page) {
        return GetNotificationAdminPageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .build();
    }
}
