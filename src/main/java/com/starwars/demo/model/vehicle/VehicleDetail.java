package com.starwars.demo.model.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VehicleDetail {

    @JsonProperty("_id")
    private String _id;

    private String uid;
    private String description;

    @JsonProperty("__v")
    private int __v;

    private VehicleProperties properties;

}