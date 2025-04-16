package com.starwars.demo.model.starship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StarshipDetail {

    @JsonProperty("_id")
    private String id;

    private String uid;
    private String description;

    @JsonProperty("__v")
    private int version;

    private StarshipProperties properties;

}