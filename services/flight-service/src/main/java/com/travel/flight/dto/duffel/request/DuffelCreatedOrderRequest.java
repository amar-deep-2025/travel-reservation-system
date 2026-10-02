package com.travel.flight.dto.duffel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class DuffelCreatedOrderRequest {

    @JsonProperty("selected_offers")
    private List<String> selectedOffers;

    private List<PassengerRequest> passengers;

    private String type;
}
