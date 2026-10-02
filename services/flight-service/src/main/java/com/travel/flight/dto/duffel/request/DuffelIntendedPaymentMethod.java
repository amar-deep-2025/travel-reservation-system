package com.travel.flight.dto.duffel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelIntendedPaymentMethod {

    private String type;

    @JsonProperty("card_id")
    private String cardId;
}
