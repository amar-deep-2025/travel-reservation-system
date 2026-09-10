package com.travel.flight.dto.duffel.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelPassengerRequest {

   @Pattern(
           regexp = "adult|Child|Infant",
           message="Passenger type must be adult, child, or infant"
   )
   private String type;

   @Min(value=0, message="Passenger age cannot be negative")
   private Integer age;

   @AssertTrue(
           message = "Either passenger type or age must be provided, but not both"
   )
   public boolean isValidPassenger() {
      return (type != null && !type.isBlank()) ^ (age != null);
   }


}
