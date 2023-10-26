package com.example.shared.database.entity;

import com.example.shared.enumeration.MessageStatus;
import com.example.shared.enumeration.NotificationType;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arguments", columnDefinition = "json")
    private String arguments;

    private String body;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "service_source", nullable = false)
    private String serviceSource;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    @Column(name = "message_id")
    private String messageId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
