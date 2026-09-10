package com.travel.flight.dto.duffel.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuffelOfferSliceResponse {

    private  String id;

    private DuffelLocationResponse origin;
    private DuffelLocationResponse destination;


    @JsonProperty("origin_type")
    private String originType;

    @JsonProperty("destination_type")
    private String destinationType;

    private String duration;

    @JsonProperty("fare_brand_name")
    private String fareBrandName;

    @JsonProperty("ngs_shelf")
    private Integer ngsShelf;

    private List<DuffelSegmentResponse> segments;

}
