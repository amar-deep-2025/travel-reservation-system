package com.travel.flight.repository;

import com.travel.flight.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByExternalId(String externalId);

    Optional<Airport> findByIataCode(String iataCode);

    boolean existsByExternalId(String externalId);

    boolean existsByIataCode(String iataCode);


}
