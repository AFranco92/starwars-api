package com.starwars.demo.people;

import com.starwars.demo.model.people.Person;
import com.starwars.demo.model.people.PersonDetail;
import com.starwars.demo.model.people.PersonProperties;
import com.starwars.demo.response.people.PeopleResponse;
import com.starwars.demo.response.people.SinglePersonResponse;
import com.starwars.demo.service.people.impl.PeopleServiceImpl;
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
public class PeopleServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PeopleServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "baseUrl", "http://localhost:8080/api");
        ReflectionTestUtils.setField(service, "scheme", "http");
        ReflectionTestUtils.setField(service, "host", "localhost");
        ReflectionTestUtils.setField(service, "path", "/api");
    }

    @Test
    void testFindAll() {
        PeopleResponse response = getPeopleResponse();
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(PeopleResponse.class))).thenReturn(response);
        Pageable pageable = PageRequest.of(0, 1);
        Page<Person> resultPage = service.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals("Luke Skywalker", response.getResults().get(0).getName());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SinglePersonResponse response = getSinglePersonResponse();
        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/people/" + id, SinglePersonResponse.class)).thenReturn(response);

        SinglePersonResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals("Luke Skywalker", result.getResult().getProperties().getName());
    }

    private static PeopleResponse getPeopleResponse() {
        Person person = new Person();
        person.setName("Luke Skywalker");
        person.setUid("1");
        person.setUrl("https://www.swapi.tech/api/people/1");

        PeopleResponse response = new PeopleResponse();
        response.setResults(List.of(person));
        return response;
    }

    private static SinglePersonResponse getSinglePersonResponse() {
        SinglePersonResponse response = new SinglePersonResponse();
        PersonDetail detail = new PersonDetail();
        PersonProperties properties = new PersonProperties();
        properties.setName("Luke Skywalker");
        detail.setProperties(properties);
        response.setResult(detail);
        return response;
    }

}
