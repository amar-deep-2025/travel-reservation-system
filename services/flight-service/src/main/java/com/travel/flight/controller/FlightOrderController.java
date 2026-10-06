package com.travel.flight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.travel.flight.dto.duffel.request.DuffelCreatedOrderRequest;
import com.travel.flight.service.FlightOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/air/orders")
@RequiredArgsConstructor
public class FlightOrderController {

    private final FlightOrderService flightOrderService;

    @PostMapping
    public JsonNode createOrder(@RequestBody DuffelCreatedOrderRequest request)throws JsonProcessingException {

        return flightOrderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public JsonNode getOrderById(@Valid  @PathVariable String orderId)throws JsonProcessingException{
        return flightOrderService.getOrderById(orderId);
    }

    @GetMapping
    public ResponseEntity<JsonNode> getAllOrders() throws JsonProcessingException{
        JsonNode response=flightOrderService.getAllOrders();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/cancellations")
    public ResponseEntity<JsonNode> createOrderCancellation(@PathVariable String orderId) throws JsonProcessingException{
        JsonNode response=flightOrderService.createOrderCancellation(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/order_cancellations/{orderId}")
    public ResponseEntity<JsonNode> getCancerOrderById(@PathVariable String orderId) throws JsonProcessingException{
        JsonNode response=flightOrderService.getCancerOrderById(orderId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/order_cancellations/{cancellationId}/confirm")
    public ResponseEntity<JsonNode> confirmCancelOrder(@PathVariable String cancellationId) throws JsonProcessingException{
        JsonNode response=flightOrderService.confirmCancelOrder(cancellationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/order_cancellations")
    public ResponseEntity<JsonNode> getAllOrderCancellations() throws JsonProcessingException{
        JsonNode response=flightOrderService.getAllOrderCancellations();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/payments/{orderId}")
    public ResponseEntity<JsonNode> getOrderPayments(
            @PathVariable String orderId) throws JsonProcessingException {

        JsonNode response = flightOrderService.getOrderPayments(orderId);

        return ResponseEntity.ok(response);
    }
}
