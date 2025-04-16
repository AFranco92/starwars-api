package com.starwars.demo.response.vehicle;

import com.starwars.demo.model.vehicle.VehicleDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleVehicleResponse extends GenericResponse {

    private VehicleDetail result;

}