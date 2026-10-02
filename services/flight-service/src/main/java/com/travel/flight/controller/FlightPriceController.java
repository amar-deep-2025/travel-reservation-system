package com.travel.flight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.flight.dto.duffel.request.DuffelPriceOfferRequest;
import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import com.travel.flight.dto.duffel.response.DuffelResponse;
import com.travel.flight.service.FlightPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("air/offers")
@RequiredArgsConstructor
public class FlightPriceController {

    private final FlightPriceService flightPriceService;

    @PostMapping("/{offerId}/price")
    public DuffelOfferResponse priceOffer(@PathVariable String offerId,
                                                   @RequestBody DuffelPriceOfferRequest request)throws JsonProcessingException {
        return flightPriceService.priceOffer(offerId, request);
    }
}
