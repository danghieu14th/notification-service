package com.example.api.controller;

import com.example.api.controller.dto.request.GetNotificationAdminRequest;
import com.example.api.controller.dto.request.GetNotificationCustomerRequest;
import com.example.api.controller.dto.request.SendNotificationRequest;
import com.example.api.service.NotificationService;
import com.example.api.service.UserService;
import com.example.shared.response.CommonResponse;
import com.example.shared.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;

    @PostMapping("/internal/notification/send")
    public ResponseEntity<?> send(@RequestBody @Validated SendNotificationRequest request) {
        notificationService.send(request.toInput());
        return ResponseEntity.ok(new CommonResponse<>(null));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> send(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/admin/notification")
    public ResponseEntity<?> getNotificationAdmin(@Validated GetNotificationAdminRequest request) {

        Pageable pageable = PageableUtils.generate(request.getPage(),
                request.getSize(), request.getSortBy() == null ? "-created_at" : request.getSortBy());
        return ResponseEntity.ok(new CommonResponse<Object>(notificationService.getNotificationAdminPage(request.toInput(), pageable)));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getNotificationCustomer(@Validated GetNotificationCustomerRequest request) {

        Pageable pageable = PageableUtils.generate(request.getPage(),
                request.getSize(), request.getSortBy() == null ? "-created_at" : request.getSortBy());
        return ResponseEntity.ok(new CommonResponse<Object>(notificationService.getNotificationCustomerPage(request.toInput(), pageable)));
    }
}
