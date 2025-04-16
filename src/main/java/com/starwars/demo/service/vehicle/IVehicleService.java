package com.starwars.demo.service.vehicle;

import com.starwars.demo.model.vehicle.Vehicle;
import com.starwars.demo.response.vehicle.SingleVehicleResponse;
import com.starwars.demo.response.vehicle.VehiclesSearchedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVehicleService {

    Page<Vehicle> findAll(Pageable pageable);
    SingleVehicleResponse findById(Long id);
    VehiclesSearchedResponse findByName(String name);
    SingleVehicleResponse findByIdAndName(Long id, String name);

}
