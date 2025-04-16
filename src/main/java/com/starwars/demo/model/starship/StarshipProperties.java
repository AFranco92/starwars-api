package com.starwars.demo.model.starship;

import lombok.Data;

import java.util.List;

@Data
public class StarshipProperties {

    private String name;
    private String model;
    private String starshipClass;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private String crew;
    private String passengers;
    private String maxAtmospheringSpeed;
    private String hyperdriveRating;
    private String MGLT;
    private String cargoCapacity;
    private String consumables;
    private List<String> films;
    private List<String> pilots;
    private String url;
    private String created;
    private String edited;

}