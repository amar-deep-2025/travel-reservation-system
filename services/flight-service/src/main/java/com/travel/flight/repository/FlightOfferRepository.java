package com.travel.flight.repository;

import com.travel.flight.entity.FlightOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightOfferRepository extends JpaRepository<FlightOffer, Long> {

    Optional<FlightOffer> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);

    Optional<FlightOffer> findByExternalIdAndExpiresAtAfter(String externalId, LocalDateTime currentTime);
}
