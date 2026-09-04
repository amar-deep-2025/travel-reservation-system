package com.travel.flight.repository;

import com.travel.flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);
}
