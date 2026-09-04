package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "flight_offers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_flight_offer_external_id",
                        columnNames = "external_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Duffel Offer ID
    @Column(name = "external_id", nullable = false, length = 100)
    private String externalId;

    // Internal Airline ID - Duffel Offer Owner
    @Column(name = "airline_id", nullable = false)
    private Long airlineId;

    // Duffel base_amount
    @Column(
            name = "base_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal baseAmount;

    // Duffel base_currency
    @Column(name = "base_currency", nullable = false, length = 3)
    private String baseCurrency;

    // Duffel tax_amount
    @Column(
            name = "tax_amount",
            precision = 12,
            scale = 2
    )
    private BigDecimal taxAmount;

    // Duffel tax_currency
    @Column(name = "tax_currency", length = 3)
    private String taxCurrency;

    // Duffel total_amount
    @Column(
            name = "total_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal totalAmount;

    // Duffel total_currency
    @Column(name = "total_currency", nullable = false, length = 3)
    private String totalCurrency;

    // Duffel total_emissions_kg
    @Column(
            name = "total_emission_kg",
            precision = 12,
            scale = 2
    )
    private BigDecimal totalEmissionKg;

    // Duffel expires_at
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    // Duffel created_at
    @Column(name = "provider_created_at")
    private LocalDateTime providerCreatedAt;

    // Duffel updated_at
    @Column(name = "provider_updated_at")
    private LocalDateTime providerUpdatedAt;

    // Duffel live_mode
    @Column(name = "live_mode", nullable = false)
    private boolean liveMode;

    // Duffel passenger_identity_documents_required
    @Column(
            name = "passenger_identity_documents_required",
            nullable = false
    )
    private boolean passengerIdentityDocumentsRequired;

    // Duffel partial
    @Column(nullable = false)
    private boolean partial;

    // Internal audit field
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    // Internal audit field
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