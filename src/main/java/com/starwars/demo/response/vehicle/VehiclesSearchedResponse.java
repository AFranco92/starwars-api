package com.starwars.demo.response.vehicle;

import com.starwars.demo.model.vehicle.VehicleDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VehiclesSearchedResponse extends GenericResponse {

    private List<VehicleDetail> result;

}