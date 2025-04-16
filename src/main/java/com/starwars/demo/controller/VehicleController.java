package com.starwars.demo.controller;

import com.starwars.demo.exception.ErrorResponse;
import com.starwars.demo.model.vehicle.Vehicle;
import com.starwars.demo.response.vehicle.SingleVehicleResponse;
import com.starwars.demo.response.vehicle.VehiclesSearchedResponse;
import com.starwars.demo.service.vehicle.IVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private final IVehicleService service;

    /**
     * Retrieve vehicles paginated.
     *
     * @param pageable The pagination information
     * @return a page of vehicles
     */
    @Operation(summary = "Retrieve vehicles paginated.", description = "Return vehicles paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vehicle.class))),
            @ApiResponse(responseCode = "404", description = "Vehicles not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("")
    public Page<Vehicle> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Search a vehicle by its ID.
     *
     * @param id ID of the vehicle to search
     * @return the vehicle if found
     */
    @Operation(summary = "Search a vehicle by ID.", description = "Returns a vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleVehicleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public SingleVehicleResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Search a vehicle by its name.
     *
     * @param name Name of the vehicle to search
     * @return the vehicle if found
     */
    @Operation(summary = "Search a vehicle by name.", description = "Returns a vehicle by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehiclesSearchedResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public VehiclesSearchedResponse findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    /**
     * Search a vehicle by its ID and name.
     *
     * @param id   ID of the vehicle to search
     * @param name Name of the vehicle to search
     * @return the vehicle if found
     */
    @Operation(summary = "Search a vehicle by ID and name.", description = "Returns a vehicle by its ID and name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleVehicleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/filter")
    public SingleVehicleResponse findByIdAndName(@RequestParam Long id, @RequestParam String name) {
        return service.findByIdAndName(id, name);
    }

}
