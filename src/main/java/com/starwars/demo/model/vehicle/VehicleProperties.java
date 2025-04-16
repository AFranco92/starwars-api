package com.starwars.demo.model.vehicle;

import lombok.Data;

import java.util.List;

@Data
public class VehicleProperties {

    private String name;
    private String model;
    private String manufacturer;
    private String costInCredits;
    private String length;
    private String maxAtmospheringSpeed;
    private String crew;
    private String passengers;
    private String cargoCapacity;
    private String consumables;
    private String vehicleClass;
    private List<String> films;
    private List<String> pilots;
    private String url;
    private String created;
    private String edited;

}