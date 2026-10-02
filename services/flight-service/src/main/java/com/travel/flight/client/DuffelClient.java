package com.travel.flight.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.flight.dto.duffel.request.*;
import com.travel.flight.dto.duffel.response.DuffelOfferRequestDataResponse;
import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import com.travel.flight.dto.duffel.response.DuffelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;

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
    public DuffelOfferResponse getOffer(String offerId) throws  JsonProcessingException{

        String response=duffelClient.get()
                .uri("air/offers/{offerId}", offerId)
                .retrieve()
                .body(String.class);

        JavaType responseType=objectMapper.getTypeFactory()
                .constructParametricType(DuffelResponse.class,
                        DuffelOfferResponse.class);

        DuffelResponse<DuffelOfferResponse> duffelResponse=objectMapper.readValue(response, responseType);

        return duffelResponse.getData();
    }
    public DuffelOfferResponse priceOffer(String offerId, DuffelPriceOfferRequest request) throws JsonProcessingException{

        DuffelPriceOfferRequestWrapper wrapper=new DuffelPriceOfferRequestWrapper(request);

        String respone=duffelClient.post()
                .uri("air/offers/{offerId}/actions/price", offerId)
                .body(wrapper)
                .retrieve()
                .body(String.class);

        JavaType responseType=objectMapper.getTypeFactory()
                .constructParametricType(
                        DuffelResponse.class,
                        DuffelOfferResponse.class
                );

        DuffelResponse<DuffelOfferResponse> duffelResponse=objectMapper.readValue(respone, responseType);

        return duffelResponse.getData();

    }

    public JsonNode createOrder(DuffelCreatedOrderRequest request)throws JsonProcessingException {

        DuffelCreateOrderRequestWrapper wrapper = new DuffelCreateOrderRequestWrapper(request);
        try {
            String requestJson = objectMapper.writeValueAsString(wrapper);
            System.out.println("Request: " + requestJson);
            String response = duffelClient.post()
                    .uri("air/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(wrapper)
                    .retrieve()
                    .body(String.class);

            JsonNode node = objectMapper.readTree(response);
            System.out.println("After Node: " + node);
            return node;
        }catch(HttpClientErrorException e){
            System.out.println("Duffel status: "+e.getStatusCode());
            System.out.println("Duffel Error body: "+e.getResponseBodyAsString());
            throw  e;
        }
    }

}