package com.travel.flight.mapper;

import com.travel.flight.dto.duffel.response.DuffelOfferResponse;
import com.travel.flight.dto.duffel.response.FlightOfferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightOfferMapper {

    private final FlightCommonMapper commonMapper;

    public FlightOfferResponse mapOffer(
            DuffelOfferResponse offer) {

        return FlightOfferResponse.builder()
                .offerId(offer.getId())
                .baseAmount(offer.getBaseAmount())
                .baseCurrency(offer.getBaseCurrency())
                .taxAmount(offer.getTaxAmount())
                .taxCurrency(offer.getTaxCurrency())
                .totalAmount(offer.getTotalAmount())
                .currency(offer.getTotalCurrency())
                .totalEmissionsKg(offer.getTotalEmissionsKg())
                .expiresAt(offer.getExpiresAt())
                .airline(commonMapper.mapAirline(offer.getOwner()))
                .passengerIdentityDocumentRequired(
                        offer.isPassengerIdentityDocumentRequired()
                )
                .slices(commonMapper.mapSlices(offer.getSlices()))
                .build();
    }
}