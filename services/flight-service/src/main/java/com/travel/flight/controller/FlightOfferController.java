package com.travel.flight.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import com.travel.flight.dto.duffel.response.FlightOfferResponse;
import com.travel.flight.service.FlightOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/air/offers")
@RequiredArgsConstructor
public class FlightOfferController {

    private final FlightOfferService flightOfferService;

    @GetMapping("/{offerId}")
    public FlightOfferResponse getOffer(@PathVariable String offerId) throws JsonProcessingException {
        return flightOfferService.getOffer(offerId);
    }

}
