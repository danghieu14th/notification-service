package com.example.shared.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "template")
@Getter
@Setter
public class Template extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel")
    private String channel;

    @ManyToOne
    @JoinColumn(name = "provider_integration_id")
    private ProviderIntegration providerIntegration;

    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "title")
    private String title;

    @Column(name = "body", columnDefinition = "text")
    private String body;
}
