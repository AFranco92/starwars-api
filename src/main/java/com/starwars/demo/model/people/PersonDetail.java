package com.starwars.demo.model.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonDetail {

    @JsonProperty("_id")
    private String _id;

    private String uid;
    private String description;

    @JsonProperty("__v")
    private int __v;

    private PersonProperties properties;

}
