package com.travel.flight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.dto.duffel.request.DuffelCreatedOrderRequest;
import com.travel.flight.service.FlightOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/air/orders")
@RequiredArgsConstructor
public class FlightOrderController {

    private final FlightOrderService flightOrderService;

    @PostMapping
    public JsonNode createOrder(@RequestBody DuffelCreatedOrderRequest request)throws JsonProcessingException {

        return flightOrderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public JsonNode getOrderById(@Valid  @PathVariable String orderId)throws JsonProcessingException{
        return flightOrderService.getOrderById(orderId);
    }
}
