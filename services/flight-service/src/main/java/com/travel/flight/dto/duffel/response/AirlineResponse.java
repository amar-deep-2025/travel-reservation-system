package com.travel.flight.dto.duffel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirlineResponse {

    private String id;
    private String iataCode;
    private String name;
    private String logoUrl;
}
