package com.example.shared.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "provider_integration")
@Getter
@Setter
public class ProviderIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "config", columnDefinition = "json")
    private String config;
}