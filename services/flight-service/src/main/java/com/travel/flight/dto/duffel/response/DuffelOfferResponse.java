package com.travel.flight.dto.duffel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class DuffelOfferResponse {

    private String id;

    private DuffelAirlineResponse owner;

    @JsonProperty("base_amount")
    private BigDecimal baseAmount;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("tax_amount")
    private BigDecimal taxAmount;

    @JsonProperty("tax_currency")
    private String taxCurrency;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("total_currency")
    private String totalCurrency;

    @JsonProperty("total_emissions_kg")
    private BigDecimal totalEmissionsKg;

    @JsonProperty("expires_at")
    private OffsetDateTime expiresAt;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @JsonProperty("live_mode")
    private boolean liveMode;

    private boolean partial;

    @JsonProperty("passenger_identity_documents_required")
    private boolean passengerIdentityDocumentRequired;

    private List<DuffelPassengerResponse> passengers;

    private List<DuffelOfferSliceResponse> slices;

    @JsonProperty("supported_loyalty_programmes")
    private List<String> supportedLoyaltyProgrammes;

    @JsonProperty("supported_passenger_identity_document_types")
    private List<String> supportedPassengerIdentityDocumentTypes;


}
