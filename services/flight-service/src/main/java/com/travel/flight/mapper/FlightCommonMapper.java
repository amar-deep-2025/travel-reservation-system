package com.travel.flight.mapper;

import com.travel.flight.dto.duffel.response.*;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightCommonMapper {

    public AirlineResponse mapAirline(
            DuffelAirlineResponse airline) {

        if (airline == null) {
            return null;
        }

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .name(airline.getName())
                .logoUrl(airline.getLogoSymbolUrl())
                .build();
    }

    public List<FlightSliceResponse> mapSlices(
            List<DuffelOfferSliceResponse> slices) {

        if (slices == null) {
            return List.of();
        }

        return slices.stream()
                .map(this::mapSlice)
                .toList();
    }

    private FlightSliceResponse mapSlice(
            DuffelOfferSliceResponse slice) {

        return FlightSliceResponse.builder()
                .origin(slice.getOrigin() != null
                        ? slice.getOrigin().getIataCode()
                        : null)
                .destination(slice.getDestination() != null
                        ? slice.getDestination().getIataCode()
                        : null)
                .duration(slice.getDuration())
                .segments(mapSegments(slice.getSegments()))
                .build();
    }

    private List<FlightSegmentResponse> mapSegments(
            List<DuffelSegmentResponse> segments) {

        if (segments == null) {
            return List.of();
        }

        return segments.stream()
                .map(this::mapSegment)
                .toList();
    }

    private FlightSegmentResponse mapSegment(
            DuffelSegmentResponse segment) {

        return FlightSegmentResponse.builder()
                .flightNumber(
                        segment.getMarketingCarrierFlightNumber()
                )
                .origin(segment.getOrigin() != null
                        ? segment.getOrigin().getIataCode()
                        : null)
                .destination(segment.getDestination() != null
                        ? segment.getDestination().getIataCode()
                        : null)
                .originTerminal(segment.getOriginTerminal())
                .destinationTerminal(segment.getDestinationTerminal())
                .departureAt(segment.getDepartingAt())
                .arrivalAt(segment.getArrivingAt())
                .duration(segment.getDuration())
                .marketingCarrier(
                        mapAirline(segment.getMarketingCarrier())
                )
                .operatingCarrier(
                        mapAirline(segment.getOperatingCarrier())
                )
                .build();
    }
}