package com.travel.flight.dto.duffel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuffelPriceOfferRequest {

    @JsonProperty("intended_payment_method")
    private List<DuffelIntendedPaymentMethod> intendedPaymentMethod;

    @JsonProperty("intended_services")
    private List<DuffelIntendedService> intendedServices;

}
