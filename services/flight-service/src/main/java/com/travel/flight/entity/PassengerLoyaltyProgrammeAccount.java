package com.travel.flight.entity;

import com.travel.flight.enums.LoyaltyTier;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name="passenger_loyalty_programme_accounts",
        uniqueConstraints= {
                @UniqueConstraint(
                        name = "uk_passenger_loyalty_programme_account",
                        columnNames = {
                                "passenger_id",
                                "airline_iata_code",
                                "account_number"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerLoyaltyProgrammeAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="passenger_id", nullable = false)
    private Long passengerId;

    @Column(name="airline_iata_code", nullable = false, length = 2)
    private String airlineIataCode;


    @Column(name="account_number", nullable = false, length = 30)
    private String accountNumber;

    //Internal loyalty
    @Enumerated(EnumType.STRING)
    @Column(name="tier", nullable = false, length = 20)
    @Builder.Default
    private LoyaltyTier loyaltyTier= LoyaltyTier.BASIC;

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
