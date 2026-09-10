package com.travel.flight.dto.duffel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DuffelLocationResponse {

    private String id;

    private String type;

    @JsonProperty("iata_code")
    private String iataCode;

    @JsonProperty("icao_code")
    private String icaoCode;

    @JsonProperty("iata_city_code")
    private String iataCityCode;

    @JsonProperty("iata_country_code")
    private String iataCountryCode;

    @JsonProperty("city_name")
    private String cityName;

    private String name;

    @JsonProperty("time_zone")
    private String timeZone;

    private BigDecimal latitude;

    private BigDecimal longitude;

}
