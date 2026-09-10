package com.travel.flight.dto.duffel.requests;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DuffelOfferRequestWrapper {

    @Valid
    private DuffelOfferRequest data;
}
