package com.travel.flight.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.client.DuffelClient;
import com.travel.flight.dto.duffel.request.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final DuffelClient duffelClient;

    public JsonNode createPayment(PaymentRequestDto requestDto) throws JsonProcessingException {
        return duffelClient.createPayment(requestDto);
    }
    public JsonNode getOrderPayments(String orderId) throws JsonProcessingException {
        return duffelClient.getOrderPayments(orderId);
    }
    public JsonNode getPaymentByPaymentId(String paymentId) throws JsonProcessingException {
        return duffelClient.getPaymentByPaymentId(paymentId);
    }
}
