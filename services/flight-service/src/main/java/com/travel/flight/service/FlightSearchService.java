package com.travel.flight.service;

import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.requests.DuffelOfferRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final DuffelClient duffelClient;

    public String searchFlights(DuffelOfferRequest request) {
        return duffelClient.searchFlights(request);
    }
}
