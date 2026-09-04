package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "flight_offer_slice_segments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_offer_slice_segment",
                        columnNames = {
                                "flight_offer_slice_id",
                                "flight_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightOfferSliceSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Internal FlightOfferSlice ID
    @Column(name = "flight_offer_slice_id", nullable = false)
    private Long flightOfferSliceId;

    // Internal Flight ID
    // Flight represents a Duffel segment
    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    // Position of segment inside the slice
    @Column(name = "segment_order", nullable = false)
    private Integer segmentOrder;

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