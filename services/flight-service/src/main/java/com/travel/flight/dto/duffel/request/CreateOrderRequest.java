package com.travel.flight.dto.duffel.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private String offerId;
    private List<PassengerRequest> passengers;
}
