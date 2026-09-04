package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name="flights",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="uk_flight_external_id",
                        columnNames = "external_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, length = 100)
    private String externalId;

    @Column(name="airline_id", nullable = false)
    private Long airlineId;

    @Column(name="operating_airline_id")
    private Long operatingAirlineId;

    @Column(name="aircraft_id")
    private Long aircraftId;

    @Column(name="flight_number", nullable = false, length = 20)
    private String flightNumber;

    @Column(name="origin_airport_id", nullable = false)
    private Long originAirportId;

    @Column(name="destination_airport_id", nullable = false)
    private Long destinationAirportId;

    @Column(name="origin_terminal", length = 20)
    private String originTerminal;

    @Column(name="destination_terminal", length = 20)
    private String destinationTerminal;

    @Column(name="departing_at", nullable = false)
    private LocalDateTime departingAt;

    @Column(name="arriving_at", nullable = false)
    private LocalDateTime arrivingAt;

    @Column(name="duration_minutes")
    private Integer durationMinutes;

    @Column(precision = 12, scale = 2)
    private BigDecimal distance;

    @Column(nullable = false)
    @Builder.Default
    private boolean active=true;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
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

