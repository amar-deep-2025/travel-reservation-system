package com.travel.flight.repository;

import com.travel.flight.entity.OfferRequestPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRequestPassengerRepository extends JpaRepository<OfferRequestPassenger, Long> {

    List<OfferRequestPassenger> findByOfferRequestId(Long offerRequestId);
}
