package com.travel.flight.dto.duffel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelSliceRequest {

    @NotBlank(message="Origin is required")
    @Pattern(regexp = "^[A-Za-z]{3}",
    message = "Origin must be a 3-letter IATA code"
    )
    private String origin;

    @NotBlank(message="Destination is required")
    @Pattern(regexp = "^[A-Za-z]{3}",
            message = "Destination must be a 3-letter IATA code"
    )
    private String destination;

    @NotBlank(message="Departure date is required")
    @JsonProperty("departure_date")
    private String departureDate;
}
