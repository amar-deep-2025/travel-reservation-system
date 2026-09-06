package com.travel.flight.controller;

import com.travel.flight.dto.duffel.requests.DuffelOfferRequestWrapper;
import com.travel.flight.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<String> searchFlights(@RequestBody DuffelOfferRequestWrapper request){
        String response= flightSearchService.searchFlights(request.getData());

        return ResponseEntity.ok(response);
    }
}
