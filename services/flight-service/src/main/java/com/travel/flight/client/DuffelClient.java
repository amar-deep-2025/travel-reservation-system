package com.travel.flight.client;

import com.travel.flight.dto.duffel.requests.DuffelOfferRequest;
import com.travel.flight.dto.duffel.requests.DuffelOfferRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class DuffelClient {

    private final RestClient duffelClient;

    public String searchFlights(DuffelOfferRequest request){
        DuffelOfferRequestWrapper wrapper=new DuffelOfferRequestWrapper(request);
        return duffelClient.post()
                .uri("air/offer_requests")
                .body(wrapper)
                .retrieve()
                .body(String.class);
    }




}
