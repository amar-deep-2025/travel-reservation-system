package com.travel.flight.dto.duffel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Getter
@Setter
@Builder
public class FlightSegmentResponse {

    private String flightNumber;
    private String origin;
    private String destination;

    private String originTerminal;
    private String destinationTerminal;

    private LocalDateTime departureAt;
    private LocalDateTime arrivalAt;

    private String duration;

    private AirlineResponse marketingCarrier;
    private AirlineResponse operatingCarrier;
}
