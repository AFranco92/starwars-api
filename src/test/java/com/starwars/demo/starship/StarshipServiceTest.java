package com.starwars.demo.starship;

import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.model.starship.StarshipDetail;
import com.starwars.demo.model.starship.StarshipProperties;
import com.starwars.demo.response.starship.SingleStarshipResponse;
import com.starwars.demo.response.starship.StarshipResponse;
import com.starwars.demo.service.starship.impl.StarshipServiceImpl;
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
public class StarshipServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StarshipServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "baseUrl", "http://localhost:8080/api");
        ReflectionTestUtils.setField(service, "scheme", "http");
        ReflectionTestUtils.setField(service, "host", "localhost");
        ReflectionTestUtils.setField(service, "path", "/api");
    }

    @Test
    void testFindAll() {
        StarshipResponse response = getStarshipResponse();
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(StarshipResponse.class))).thenReturn(response);

        Pageable pageable = PageRequest.of(0, 1);
        Page<Starship> resultPage = service.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals("CR90 corvette", resultPage.getContent().get(0).getName());

    }

    @Test
    void testFindById() {
        Long id = 2L;
        SingleStarshipResponse response = getSingleStarshipResponse();
        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/starships/" + id, SingleStarshipResponse.class)).thenReturn(response);

        SingleStarshipResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals("CR90 corvette", result.getResult().getProperties().getName());
    }

    private static StarshipResponse getStarshipResponse() {
        StarshipResponse response = new StarshipResponse();
        Starship starship = new Starship();
        starship.setUid("2");
        starship.setName("CR90 corvette");
        starship.setUrl("https://www.swapi.tech/api/starships/2");
        response.setResults(List.of(starship));
        return response;
    }

    private static SingleStarshipResponse getSingleStarshipResponse() {
        SingleStarshipResponse response = new SingleStarshipResponse();
        StarshipDetail detail = new StarshipDetail();
        StarshipProperties properties = new StarshipProperties();
        properties.setName("CR90 corvette");
        detail.setProperties(properties);
        response.setResult(detail);
        return response;
    }

}
