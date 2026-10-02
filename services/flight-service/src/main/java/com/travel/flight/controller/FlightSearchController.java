package com.travel.flight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.dto.duffel.request.DuffelOfferRequestWrapper;
import com.travel.flight.dto.duffel.response.FlightSearchResponse;
import com.travel.flight.service.FlightSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/air/flights")
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponse>> searchFlights(
            @Valid  @RequestBody DuffelOfferRequestWrapper request)
            throws JsonProcessingException {

        List<FlightSearchResponse> response =
                flightSearchService.searchFlights(request.getData());

        return ResponseEntity.ok(response);
    }

}
