package com.travel.flight.repository;

import com.travel.flight.entity.FlightOfferSliceSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightOfferSliceSegmentRepository extends JpaRepository<FlightOfferSliceSegment, Long> {

    List<FlightOfferSliceSegment> findByFlightOfferSliceId(Long flightOfferSliceId);

    List<FlightOfferSliceSegment> findByFlightId(Long flightId);

    boolean existsByFlightOfferSliceIdAndFlightId(Long flightOfferSliceId, Long flightId);

}
