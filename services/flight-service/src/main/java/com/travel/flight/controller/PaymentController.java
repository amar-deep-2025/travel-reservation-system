package com.travel.flight.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.dto.duffel.request.PaymentRequestDto;
import com.travel.flight.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/air/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<JsonNode> createPayment(@Valid @RequestBody PaymentRequestDto requestDto) throws JsonProcessingException {
        JsonNode response= paymentService.createPayment(requestDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<JsonNode> getOrderPayments(
            @PathVariable String orderId) throws JsonProcessingException {

        JsonNode response = paymentService.getOrderPayments(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<JsonNode> getPaymentByPaymentId(
            @PathVariable String paymentId) throws JsonProcessingException {

        JsonNode response = paymentService.getPaymentByPaymentId(paymentId);

        return ResponseEntity.ok(response);
    }
}
