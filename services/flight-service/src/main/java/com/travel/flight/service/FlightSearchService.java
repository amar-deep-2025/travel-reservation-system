package com.travel.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.requests.DuffelOfferRequest;

import com.travel.flight.dto.duffel.response.DuffelOfferRequestDataResponse;
import com.travel.flight.dto.duffel.response.DuffelResponse;
import com.travel.flight.dto.duffel.response.FlightSearchResponse;
import com.travel.flight.mapper.FlightSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final DuffelClient duffelClient;
    private final FlightSearchMapper flightSearchMapper;
    public List<FlightSearchResponse> searchFlights(
            DuffelOfferRequest request) throws JsonProcessingException {

        DuffelResponse<DuffelOfferRequestDataResponse> duffelResponse =
                duffelClient.searchFlights(request);

        return flightSearchMapper.mapOffers(
                duffelResponse.getData()
        );
    }
}
