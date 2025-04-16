package com.starwars.demo.vehicle;

import com.starwars.demo.model.vehicle.Vehicle;
import com.starwars.demo.model.vehicle.VehicleDetail;
import com.starwars.demo.model.vehicle.VehicleProperties;
import com.starwars.demo.response.vehicle.SingleVehicleResponse;
import com.starwars.demo.response.vehicle.VehiclesResponse;
import com.starwars.demo.service.vehicle.impl.VehicleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VehicleServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "baseUrl", "http://localhost:8080/api");
        ReflectionTestUtils.setField(service, "scheme", "http");
        ReflectionTestUtils.setField(service, "host", "localhost");
        ReflectionTestUtils.setField(service, "path", "/api");
    }

    @Test
    void findAll() {
        VehiclesResponse response = getVehiclesResponse();

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(VehiclesResponse.class))).thenReturn(response);

        Pageable pageable = PageRequest.of(0, 1);
        Page<Vehicle> pageResult = service.findAll(pageable);

        assertNotNull(pageResult);
        assertEquals("Sand Crawler", pageResult.getContent().get(0).getName());

    }

    @Test
    void findById() {
        Long id = 4L;
        SingleVehicleResponse response = getSingleVehicleResponse();
        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/vehicles/" + id, SingleVehicleResponse.class)).thenReturn(response);

        SingleVehicleResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals("Sand Crawler", result.getResult().getProperties().getName());
    }

    private static VehiclesResponse getVehiclesResponse() {
        VehiclesResponse response = new VehiclesResponse();
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Sand Crawler");
        response.setResults(List.of(vehicle));
        return response;
    }

    private static SingleVehicleResponse getSingleVehicleResponse() {
        SingleVehicleResponse response = new SingleVehicleResponse();
        VehicleDetail detail = new VehicleDetail();
        VehicleProperties properties = new VehicleProperties();
        properties.setName("Sand Crawler");
        detail.setProperties(properties);
        response.setResult(detail);
        return response;
    }

}
