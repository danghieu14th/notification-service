package com.example.shared.database.repository;

import com.example.shared.database.entity.Notification;
import com.example.shared.database.entity.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
    Page<NotificationHistory> findByNotification(Notification notification, Pageable pageable);
}
