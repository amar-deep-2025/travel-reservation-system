package com.travel.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import com.travel.flight.dto.duffel.response.FlightOfferResponse;
import com.travel.flight.mapper.FlightOfferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightOfferService {

    private final DuffelClient duffelClient;
    private final FlightOfferMapper flightOfferMapper;

    public FlightOfferResponse getOffer(String offerId) throws JsonProcessingException {

        DuffelOfferResponse offer= duffelClient.getOffer(offerId);
        return flightOfferMapper.mapOffer(offer);
    }
}
