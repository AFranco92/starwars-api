package com.starwars.demo.starship;

import com.starwars.demo.controller.StarshipController;
import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.response.starship.SingleStarshipResponse;
import com.starwars.demo.response.starship.StarshipsSearchedResponse;
import com.starwars.demo.service.starship.IStarshipService;
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
public class StarshipControllerTest {

    @Mock
    private IStarshipService starshipService;

    @InjectMocks
    private StarshipController starshipController;

    @Test
    void testFindAll() {
        Page<Starship> mockPage = new PageImpl<>(Collections.singletonList(new Starship()));
        Pageable pageable = Pageable.unpaged();
        when(starshipService.findAll(pageable)).thenReturn(mockPage);

        Page<Starship> result = starshipController.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        verify(starshipService, times(1)).findAll(pageable);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SingleStarshipResponse expected = new SingleStarshipResponse();
        when(starshipService.findById(id)).thenReturn(expected);

        SingleStarshipResponse result = starshipController.findById(id);

        assertEquals(expected, result);
        verify(starshipService, times(1)).findById(id);
    }

    @Test
    void testFindByName() {
        String name = "Millennium Falcon";
        StarshipsSearchedResponse expected = new StarshipsSearchedResponse();
        when(starshipService.findByName(name)).thenReturn(expected);

        StarshipsSearchedResponse result = starshipController.findByName(name);

        assertEquals(expected, result);
        verify(starshipService, times(1)).findByName(name);
    }

    @Test
    void testFindByIdAndName() {
        Long id = 1L;
        String name = "Millennium Falcon";
        SingleStarshipResponse expected = new SingleStarshipResponse();
        when(starshipService.findByIdAndName(id, name)).thenReturn(expected);

        SingleStarshipResponse result = starshipController.findByIdAndName(id, name);

        assertEquals(expected, result);
        verify(starshipService, times(1)).findByIdAndName(id, name);
    }
}