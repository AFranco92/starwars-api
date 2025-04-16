package com.starwars.demo.response.vehicle;

import com.starwars.demo.model.vehicle.Vehicle;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VehiclesResponse extends GenericResponse {

    private int totalRecords;
    private int totalPages;
    private String previous;
    private String next;
    private List<Vehicle> results;

}