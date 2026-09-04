package com.travel.flight.repository;

import com.travel.flight.entity.OfferRequestSlice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRequestSliceRepository extends JpaRepository<OfferRequestSlice, Long> {


    List<OfferRequestSlice> findByOfferRequestId(Long offerRequestId);
}
