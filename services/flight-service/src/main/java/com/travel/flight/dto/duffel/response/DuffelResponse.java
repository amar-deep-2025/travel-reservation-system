package com.travel.flight.dto.duffel.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuffelResponse<T>{

    private T data;


}
