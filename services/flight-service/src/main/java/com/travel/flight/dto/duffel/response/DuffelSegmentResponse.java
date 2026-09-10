package com.travel.flight.dto.duffel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Getter
@Setter
public class DuffelSegmentResponse {

    @JsonProperty("marketing_carrier")
    private DuffelAirlineResponse marketingCarrier;

    @JsonProperty("operating_carrier")
    private DuffelAirlineResponse operatingCarrier;

    @JsonProperty("marketing_carrier_flight_number")
    private String marketingCarrierFlightNumber;

    @JsonProperty("operating_carrier_flight_number")
    private String operatingCarrierFlightNumber;

    private DuffelLocationResponse origin;

    private DuffelLocationResponse destination;

    @JsonProperty("origin_terminal")
    private String originTerminal;

    @JsonProperty("destination_terminal")
    private String destinationTerminal;

    @JsonProperty("departing_at")
    private LocalDateTime departingAt;

    @JsonProperty("arriving_at")
    private LocalDateTime arrivingAt;

    private String duration;

    private BigDecimal distance;

//    private DufferlAirCraftResponse aircraft;
//
//    private List<DuffelStopResponse> stops;
}
