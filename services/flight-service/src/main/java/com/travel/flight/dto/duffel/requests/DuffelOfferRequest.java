package com.travel.flight.dto.duffel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuffelOfferRequest {

    @JsonProperty("cabin_class")
    private String cabinClass;

    @JsonProperty("max_connections")
    private Integer maxConnections;

    @JsonProperty("include_split_ticket")
    private Boolean includeSplitTicket;

    @JsonProperty("client_key")
    private String clientKey;

    @JsonProperty("airline_credit_ids")
    private List<String> airlineCreditIds;
    private List<DuffelSliceRequest> slices;
    private List<DuffelPassengerRequest> passengers;
}
