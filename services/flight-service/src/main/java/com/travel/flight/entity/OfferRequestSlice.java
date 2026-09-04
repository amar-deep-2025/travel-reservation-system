package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "offer_request_slices"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestSlice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Internal Offer Request ID
    @Column(name = "offer_request_id", nullable = false)
    private Long offerRequestId;

    // Duffel origin IATA code
    // Example: DEL, LHR, NYC
    @Column(name = "origin_code", nullable = false, length = 3)
    private String originCode;

    // Duffel destination IATA code
    // Example: BOM, JFK, LON
    @Column(name = "destination_code", nullable = false, length = 3)
    private String destinationCode;

    // Requested departure date
    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    // Optional departure time window
    @Column(name = "departure_time_from")
    private LocalTime departureTimeFrom;

    @Column(name = "departure_time_to")
    private LocalTime departureTimeTo;

    // Optional arrival time window
    @Column(name = "arrival_time_from")
    private LocalTime arrivalTimeFrom;

    @Column(name = "arrival_time_to")
    private LocalTime arrivalTimeTo;

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