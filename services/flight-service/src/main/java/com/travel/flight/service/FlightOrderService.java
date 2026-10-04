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
    public JsonNode getOrderById(String orderId) throws JsonProcessingException{
        return duffelClient.getOrderById(orderId);
    }
    public JsonNode getAllOrders() throws  JsonProcessingException{
        return duffelClient.getAllOrders();
    }
    public JsonNode createOrderCancellation(String orderId) throws JsonProcessingException{
        return duffelClient.createOrderCancellation(orderId);
    }

   public JsonNode getCancerOrderById(String orderId) throws JsonProcessingException{
        return duffelClient.getOrderCancellationsById(orderId);
   }

   public JsonNode confirmCancelOrder(String cancellationId )throws JsonProcessingException{
        return duffelClient.confirmCancelOrder(cancellationId);
   }


}
