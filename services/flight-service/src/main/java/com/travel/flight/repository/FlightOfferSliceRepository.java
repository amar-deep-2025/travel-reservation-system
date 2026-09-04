package com.travel.flight.repository;

import com.travel.flight.entity.FlightOfferSlice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightOfferSliceRepository extends JpaRepository<FlightOfferSlice, Long> {

    List<FlightOfferSlice> findByFlightOfferId(Long flightOfferId);

    Optional<FlightOfferSlice> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);
}
