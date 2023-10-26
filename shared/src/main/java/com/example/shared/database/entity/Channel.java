package com.example.shared.database.entity;

import com.example.shared.enumeration.ChannelType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "channel")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;
}