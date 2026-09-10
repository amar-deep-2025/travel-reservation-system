package com.travel.flight.dto.duffel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
@Builder
public class FlightSliceResponse {

    private String origin;
    private String destination;

    private String duration;

    private List<FlightSegmentResponse> segments;
}
