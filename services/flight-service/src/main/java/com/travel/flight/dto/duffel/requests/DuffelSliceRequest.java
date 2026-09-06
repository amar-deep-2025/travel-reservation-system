package com.travel.flight.dto.duffel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelSliceRequest {

    private String origin;

    private String destination;

    @JsonProperty("departure_date")
    private String departureDate;
}
