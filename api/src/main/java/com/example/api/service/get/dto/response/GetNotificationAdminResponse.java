package com.example.api.service.get.dto.response;

import com.example.api.service.get.dto.request.GetNotificationAdminDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class GetNotificationAdminResponse {
    List<GetNotificationAdminDto> content;

    Integer totalPages;

    Long totalElements;

    Integer size;

    public static GetNotificationAdminResponse from(Page<GetNotificationAdminDto> page) {
        return GetNotificationAdminResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .build();
    }
}
