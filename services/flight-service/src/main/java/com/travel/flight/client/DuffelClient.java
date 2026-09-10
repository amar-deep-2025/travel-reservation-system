package com.travel.flight.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.flight.dto.duffel.requests.DuffelOfferRequest;
import com.travel.flight.dto.duffel.requests.DuffelOfferRequestWrapper;
import com.travel.flight.dto.duffel.response.DuffelOfferRequestDataResponse;
import com.travel.flight.dto.duffel.response.DuffelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class DuffelClient {

    private final RestClient duffelClient;
    private final ObjectMapper objectMapper;

    public DuffelResponse<DuffelOfferRequestDataResponse> searchFlights(
            DuffelOfferRequest request) throws JsonProcessingException {

        DuffelOfferRequestWrapper wrapper =
                new DuffelOfferRequestWrapper(request);

        String response = duffelClient.post()
                .uri("air/offer_requests")
                .body(wrapper)
                .retrieve()
                .body(String.class);

        JavaType responseType = objectMapper.getTypeFactory()
                .constructParametricType(
                        DuffelResponse.class,
                        DuffelOfferRequestDataResponse.class
                );

        return objectMapper.readValue(response, responseType);
    }
}