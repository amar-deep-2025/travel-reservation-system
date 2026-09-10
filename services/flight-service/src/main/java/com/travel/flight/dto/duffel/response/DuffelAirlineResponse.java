package com.travel.flight.dto.duffel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelAirlineResponse {

    private String id;

    @JsonProperty("iata_code")
    private String iataCode;

    private String name;

    @JsonProperty("logo_symbol_url")
    private String logoSymbolUrl;

    @JsonProperty("logo_lockup_url")
    private String logoLockUrl;

    @JsonProperty("conditions_of_carriage_url")
    private String conditionsOfCarriageUrl;
}
