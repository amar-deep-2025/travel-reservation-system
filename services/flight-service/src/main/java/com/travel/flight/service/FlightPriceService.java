package com.travel.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.request.DuffelPriceOfferRequest;
import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightPriceService {

    private final DuffelClient duffelClient;

    public DuffelOfferResponse priceOffer(String offerId,
                                          DuffelPriceOfferRequest request) throws JsonProcessingException {
        return duffelClient.priceOffer(offerId, request);
    }
}
