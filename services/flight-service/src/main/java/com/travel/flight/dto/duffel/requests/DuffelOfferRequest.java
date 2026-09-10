package com.travel.flight.dto.duffel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuffelOfferRequest {

    @NotBlank(message="Cabin class is required")
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

    @Valid
    @NotEmpty(message="At least one slice is required")
    private List<DuffelSliceRequest> slices;

    @Valid
    @NotEmpty(message="At least one passenger is required")
    private List<DuffelPassengerRequest> passengers;


}
