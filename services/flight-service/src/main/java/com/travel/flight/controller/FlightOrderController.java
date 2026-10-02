package com.travel.flight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.dto.duffel.request.DuffelCreatedOrderRequest;
import com.travel.flight.service.FlightOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/air/orders")
@RequiredArgsConstructor
public class FlightOrderController {

    private final FlightOrderService flightOrderService;

    @PostMapping
    public JsonNode createOrder(@RequestBody DuffelCreatedOrderRequest request)throws JsonProcessingException {
        // Implement the logic to create a flight order
        return flightOrderService.createOrder(request);
    }
}
