package com.travel.flight.repository;

import com.travel.flight.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Optional<Aircraft> findByExternalId(String externalId);

    Optional<Aircraft> findByIataCode(String iataCode);

    boolean existsByExternalId(String externalId);

    boolean existsByIataCode(String iataCode);

}
