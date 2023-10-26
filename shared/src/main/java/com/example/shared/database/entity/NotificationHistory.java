package com.example.shared.database.entity;

import com.example.shared.enumeration.MessageStatus;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "provider_integration_id")
    private Long providerIntegrationId;

    @Column(name = "completed_at")
    private Instant completedAt;
}