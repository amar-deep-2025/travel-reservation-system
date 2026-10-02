package com.travel.flight.dto.duffel.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PassengerRequest {
    private String id;
    private String type;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    private String gender;

    @JsonProperty("born_on")
    private LocalDate bornOn;

    private String title;
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;
}
