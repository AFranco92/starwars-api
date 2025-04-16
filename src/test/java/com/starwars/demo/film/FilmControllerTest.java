package com.starwars.demo.film;

import com.starwars.demo.controller.FilmController;
import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.film.FilmResponse;
import com.starwars.demo.response.film.SingleFilmResponse;
import com.starwars.demo.service.film.IFilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {

    @InjectMocks
    private FilmController filmController;

    @Mock
    private IFilmService filmService;

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<FilmResult> films = List.of(new FilmResult(), new FilmResult());
        Page<FilmResult> filmPage = new PageImpl<>(films, pageable, films.size());

        when(filmService.findAll(pageable)).thenReturn(filmPage);

        Page<FilmResult> result = filmController.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(filmService, times(1)).findAll(pageable);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        SingleFilmResponse response = new SingleFilmResponse();
        when(filmService.findById(id)).thenReturn(response);
        SingleFilmResponse result = filmController.findById(id);
        assertEquals(response, result);
        verify(filmService).findById(id);
    }

    @Test
    void testFindByTitle() {
        String title = "A New Hope";
        FilmResponse response = new FilmResponse();
        when(filmService.findByTitle(title)).thenReturn(response);
        FilmResponse result = filmController.findByTitle(title);
        assertEquals(response, result);
        verify(filmService).findByTitle(title);
    }

    @Test
    void testFindByIdAndTitle() {
        Long id = 1L;
        String title = "A New Hope";
        SingleFilmResponse response = new SingleFilmResponse();
        when(filmService.findByIdAndTitle(id, title)).thenReturn(response);
        SingleFilmResponse result = filmController.findByIdAndTitle(id, title);
        assertEquals(response, result);
        verify(filmService).findByIdAndTitle(id, title);
    }

}
