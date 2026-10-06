package com.travel.flight.dto.duffel.request

data class PaymentDetails(
    val amount:String,
    val currency: String,
    val type: String
)
