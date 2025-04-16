package com.starwars.demo.people;

import com.starwars.demo.controller.PeopleController;
import com.starwars.demo.model.people.Person;
import com.starwars.demo.response.people.PeopleSearchedResponse;
import com.starwars.demo.response.people.SinglePersonResponse;
import com.starwars.demo.service.people.IPeopleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {

    @Mock
    private IPeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    @Test
    void testFindAll() {
        Page<Person> mockPage = new PageImpl<>(Collections.singletonList(new Person()));
        Pageable pageable = Pageable.unpaged();
        when(peopleService.findAll(pageable)).thenReturn(mockPage);

        Page<Person> result = peopleController.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(peopleService, times(1)).findAll(pageable);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SinglePersonResponse expected = new SinglePersonResponse();
        when(peopleService.findById(id)).thenReturn(expected);

        SinglePersonResponse result = peopleController.findById(id);

        assertEquals(expected, result);
        verify(peopleService, times(1)).findById(id);
    }

    @Test
    void testFindByName() {
        String name = "Luke";
        PeopleSearchedResponse expected = new PeopleSearchedResponse();
        when(peopleService.findByName(name)).thenReturn(expected);

        PeopleSearchedResponse result = peopleController.findByName(name);

        assertEquals(expected, result);
        verify(peopleService, times(1)).findByName(name);
    }

    @Test
    void testFindByIdAndName() {
        Long id = 1L;
        String name = "Luke";
        SinglePersonResponse expected = new SinglePersonResponse();
        when(peopleService.findByIdAndName(id, name)).thenReturn(expected);

        SinglePersonResponse result = peopleController.findByIdAndName(id, name);

        assertEquals(expected, result);
        verify(peopleService, times(1)).findByIdAndName(id, name);
    }
    
}