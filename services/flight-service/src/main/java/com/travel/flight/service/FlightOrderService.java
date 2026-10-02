package com.travel.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.request.DuffelCreatedOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightOrderService {

    private final DuffelClient duffelClient;

    public JsonNode createOrder(DuffelCreatedOrderRequest request)throws JsonProcessingException {

        return duffelClient.createOrder(request);
    }
}
