package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "flight_offer_slices",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_flight_offer_slice_external_id",
                        columnNames = "external_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightOfferSlice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Duffel Slice ID
    @Column(name = "external_id", nullable = false, length = 100)
    private String externalId;

    // Internal Flight Offer ID
    @Column(name = "flight_offer_id", nullable = false)
    private Long flightOfferId;

    // First airport/city of the slice
    @Column(name = "origin_airport_id", nullable = false)
    private Long originAirportId;

    // Final airport/city of the slice
    @Column(name = "destination_airport_id", nullable = false)
    private Long destinationAirportId;

    // Duffel slice duration converted to minutes
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    // Duffel fare brand name, e.g. Basic, Standard
    @Column(name = "fare_brand_name", length = 100)
    private String fareBrandName;

    // Duffel NGS shelf value
    @Column(name = "ngs_shelf")
    private Integer ngsShelf;

    @Column(name = "created_at", nullable = false, updatable = false)
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