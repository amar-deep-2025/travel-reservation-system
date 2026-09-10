package com.travel.flight.dto.duffel.response;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
@Builder
public class FlightSearchResponse {

    private String offerId;
    private BigDecimal totalAmount;
    private String currency;
    private OffsetDateTime expiresAt;
    private AirlineResponse airline;
    private List<FlightSliceResponse> slices;

}
