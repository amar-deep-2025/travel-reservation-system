package com.travel.flight.dto.duffel.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuffelOfferRequestDataResponse {

    @JsonProperty("airline_credit_ids")
    private List<String> airlineCreditIds;

    private List<DuffelOfferResponse> offers;

}
