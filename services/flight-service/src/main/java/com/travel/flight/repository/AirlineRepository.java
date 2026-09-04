package com.travel.flight.repository;

import com.travel.flight.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    boolean existsByExternalId(String externalId);

    Optional<Airline> findByExternalId(String externalId);

    Optional<Airline> findByIataCode(String iataCode);

    boolean existsByIataCode(String iataCode);
}
