package com.starwars.demo.service.vehicle.impl;

import com.starwars.demo.exception.ExternalServiceException;
import com.starwars.demo.exception.NotFoundException;
import com.starwars.demo.model.vehicle.Vehicle;
import com.starwars.demo.response.vehicle.SingleVehicleResponse;
import com.starwars.demo.response.vehicle.VehiclesResponse;
import com.starwars.demo.response.vehicle.VehiclesSearchedResponse;
import com.starwars.demo.service.vehicle.IVehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class VehicleServiceImpl implements IVehicleService {

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    private final RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.scheme}")
    private String scheme;

    @Value("${api.host}")
    private String host;

    @Value("${api.path}")
    private String path;

    public VehicleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve vehicles paginated.
     *
     * @param pageable The pagination information
     * @return a page of vehicles
     */
    @Override
    public Page<Vehicle> findAll(Pageable pageable) {
        try {
            VehiclesResponse vehiclesResponse = restTemplate.getForObject(baseUrl + "/vehicles", VehiclesResponse.class);
            if (vehiclesResponse.getResults() == null) {
                return Page.empty();
            }
            List<Vehicle> allVehicles = vehiclesResponse.getResults();
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), allVehicles.size());
            List<Vehicle> paged = allVehicles.subList(start, end);
            return new PageImpl<>(paged, pageable, allVehicles.size());
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch vehicles from external API", e);
        }
    }

    /**
     * Search a vehicle by its ID.
     *
     * @param id ID of the vehicle to search
     * @return the vehicle if found
     */
    @Override
    public SingleVehicleResponse findById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/vehicles/" + id, SingleVehicleResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Vehicle with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch vehicle from external API", e);
        }
    }

    /**
     * Search a vehicle by its name.
     *
     * @param name Name of the vehicle to search
     * @return the vehicle if found
     */
    @Override
    public VehiclesSearchedResponse findByName(String name) {
        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme(scheme)
                .host(host)
                .path(path + "/vehicles")
                .queryParam("name", name)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(uri, VehiclesSearchedResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Vehicle with name " + name + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch vehicle from external API", e);
        }
    }

    /**
     * Search a vehicle by its id and name.
     *
     * @param id ID of the vehicle to search
     * @param name Name of the vehicle to search
     * @return the vehicle if found
     */
    @Override
    public SingleVehicleResponse findByIdAndName(Long id, String name) {
        try {
            SingleVehicleResponse vehicle = findById(id);
            if (vehicle.getResult().getProperties().getName().toLowerCase().contains(name.toLowerCase())) {
                return vehicle;
            } else {
                throw new NotFoundException("No vehicle was found with ID " + id + " and name " + name + ".");
            }
        } catch (HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Vehicle with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch vehicle from external API", e);
        }
    }

}
