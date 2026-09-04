package com.travel.flight.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name="offer_request_passengers"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="offer_request_id", nullable = false)
    private Long offerRequestId;

    @Column(name="passenger_type", length=20)
    private String type;

    @Column(name="age")
    private Integer age;

    @Column(name="given_name", length=30)
    private String givenName;

    @Column(name = "family_name", length = 50)
    private String familyName;

    @Column(name="fare_type", length=30)
    private String fareType;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }


}
