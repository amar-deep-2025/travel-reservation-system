package com.travel.flight.repository;

import com.travel.flight.entity.OfferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRequestRepository extends JpaRepository<OfferRequest, Long> {

    Optional<OfferRequest> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);
}
