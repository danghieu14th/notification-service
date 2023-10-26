package com.example.shared.database.repository;

import com.example.shared.database.dto.NotificationDTO;
import com.example.shared.database.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
            select n.id as id,
            u.id as userId,
            n.title as title,
            n.body as body,
            n.created_at as createdAt,
            n.completed_at as completedAt,
            n.type as type,
            n.status as status,
            t.name as template,
            t.channel as channel,
            pi.id as providerIntegrationId,
            pi.name as providerIntegrationName
            from notification n
            inner join template t
            on n.template_id = t.id
            inner join provider_integration pi
            on t.provider_integration_id = pi.id
            inner join user u
            on n.user_id = u.id
            where 
            (:userId is null or u.id = :userId)
            and (:template is null or t.name = :template)
            and (:channel is null or t.channel = :channel)
            and (:createdFrom is null or n.created_at >= :createdFrom)
            and (:createdTo is null or n.created_at <= :createdTo)
            and (:completedFrom is null or n.completed_at >= :completedFrom)
            and (:completedTo is null or n.completed_at <= :completedTo)
            and (:type is null or n.type = :type)
            and (:status is null or n.status = :status)
            """,
            countQuery = """
                        select count(*)
                                    from notification n
            inner join template t
            on n.template_id = t.id
            inner join provider_integration pi
            on t.provider_integration_id = pi.id
            inner join user u
            on n.user_id = u.id
            where 
            (:userId is null or u.id = :userId)
            and (:template is null or t.name = :template)
            and (:channel is null or t.channel = :channel)
            and (:createdFrom is null or n.created_at >= :createdFrom)
            and (:createdTo is null or n.created_at <= :createdTo)
            and (:completedFrom is null or n.completed_at >= :completedFrom)
            and (:completedTo is null or n.completed_at <= :completedTo)
            and (:type is null or n.type = :type)
            and (:status is null or n.status = :status)
                    """, nativeQuery = true)
    Page<NotificationDTO> getNotificationDTO(@Param("userId") Long userId,
                                             @Param("template") String template,
                                             @Param("channel") String channel,
                                             @Param("createdFrom") Instant createdFrom,
                                             @Param("createdTo") Instant createdTo,
                                             @Param("completedFrom") Instant completedFrom,
                                             @Param("completedTo") Instant completedTo,
                                             @Param("type") String type,
                                             @Param("status") String status,
                                             Pageable pageable);
    Notification findByMessageId(String messageId);

}
