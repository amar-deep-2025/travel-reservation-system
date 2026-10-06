package com.travel.flight.dto.duffel.request

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentRequestDto(

    @JsonProperty("order_id")
    val orderId:String,
    val payment:PaymentDetails
)