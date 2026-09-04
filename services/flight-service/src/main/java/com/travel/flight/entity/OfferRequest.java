package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "offer_requests",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_offer_request_external_id",
                        columnNames = "external_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Duffel Offer Request ID
    @Column(name = "external_id", nullable = false, length = 100)
    private String externalId;

    // Duffel test mode / live mode
    @Column(name = "live_mode", nullable = false)
    private boolean liveMode;

    // economy, premium_economy, business, first
    @Column(name = "cabin_class", length = 30)
    private String cabinClass;

    // Maximum number of connections allowed
    @Column(name = "max_connections")
    private Integer maxConnections;

    // Whether split-ticket results are allowed
    @Column(name = "include_split_ticket", nullable = false)
    @Builder.Default
    private boolean includeSplitTicket = false;

    // Duffel client key
    @Column(name = "client_key", length = 500)
    private String clientKey;

    // Duffel created_at
    @Column(name = "provider_created_at")
    private LocalDateTime providerCreatedAt;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}