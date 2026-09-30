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
public class FlightOfferResponse {
    private String offerId;
    private BigDecimal baseAmount;
    private String baseCurrency;
    private BigDecimal taxAmount;
    private String taxCurrency;
    private BigDecimal totalAmount;
    private String currency;
    private BigDecimal totalEmissionsKg;
    private OffsetDateTime expiresAt;
    private AirlineResponse airline;
    private boolean passengerIdentityDocumentRequired;
    private List<FlightSliceResponse> slices;


}
