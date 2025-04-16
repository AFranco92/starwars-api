package com.starwars.demo.film;

import com.starwars.demo.exception.ExternalServiceException;
import com.starwars.demo.exception.NotFoundException;
import com.starwars.demo.model.film.Film;
import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.film.FilmResponse;
import com.starwars.demo.response.film.SingleFilmResponse;
import com.starwars.demo.service.film.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FilmServiceImpl filmService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(filmService, "baseUrl", "http://localhost:8080/api");
        ReflectionTestUtils.setField(filmService, "scheme", "http");
        ReflectionTestUtils.setField(filmService, "host", "localhost");
        ReflectionTestUtils.setField(filmService, "path", "/api");
    }

    @Test
    void testFindAll() {
        FilmResponse filmResponse = getFilmResponse();

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), eq(FilmResponse.class)))
                .thenReturn(filmResponse);

        Pageable pageable = PageRequest.of(0, 1);

        Page<FilmResult> resultPage = filmService.findAll(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals("A New Hope", resultPage.getContent().get(0).getProperties().getTitle());
        assertEquals(2, resultPage.getTotalElements());
    }

    @Test
    void testFindById() {
        Long filmId = 1L;
        Film film = new Film();
        film.setTitle("A New Hope");
        film.setEpisodeId(4);

        FilmResult filmResult = new FilmResult();
        filmResult.setProperties(film);

        SingleFilmResponse response = new SingleFilmResponse();
        response.setResult(filmResult);

        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/films/" + filmId, SingleFilmResponse.class))
                .thenReturn(response);

        SingleFilmResponse result = filmService.findById(filmId);

        assertNotNull(result);
        assertEquals("A New Hope", result.getResult().getProperties().getTitle());
        assertEquals(4, result.getResult().getProperties().getEpisodeId());
    }

    @Test
    void testFindByIdNotFound() {
        Long filmId = 999L;

        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/films/" + filmId, SingleFilmResponse.class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            filmService.findById(filmId);
        });

        assertEquals("Film with ID " + filmId + " not found.", exception.getMessage());
    }

    @Test
    void testFindByIdExternalServiceException() {
        Long filmId = 123L;

        Mockito.when(restTemplate.getForObject("http://localhost:8080/api/films/" + filmId, SingleFilmResponse.class))
                .thenThrow(new RestClientException("Timeout"));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            filmService.findById(filmId);
        });

        assertTrue(exception.getMessage().contains("Failed to fetch film from external API"));
    }

    @Test
    void testFindByTitle() {
        String title = "A New Hope";

        Film film = new Film();
        film.setTitle(title);
        film.setEpisodeId(4);

        FilmResult filmResult = new FilmResult();
        filmResult.setProperties(film);

        FilmResponse response = new FilmResponse();
        response.setResult(List.of(filmResult));

        ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);

        Mockito.when(restTemplate.getForObject(uriCaptor.capture(), Mockito.eq(FilmResponse.class)))
                .thenReturn(response);

        FilmResponse result = filmService.findByTitle(title);

        assertNotNull(result);
        assertEquals(1, result.getResult().size());
        assertEquals(title, result.getResult().get(0).getProperties().getTitle());
    }

    @Test
    void testFindByTitleExternalServiceException() {
        String title = "Non Existent Title";

        ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);

        Mockito.when(restTemplate.getForObject(uriCaptor.capture(), Mockito.eq(FilmResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            filmService.findByTitle(title);
        });

        assertTrue(exception.getMessage().contains("Failed to fetch films from external API"));
    }

    private static FilmResponse getFilmResponse() {
        Film film1 = new Film();
        film1.setTitle("A New Hope");
        film1.setEpisodeId(4);

        Film film2 = new Film();
        film2.setTitle("The Empire Strikes Back");
        film2.setEpisodeId(5);

        FilmResult filmResult1 = new FilmResult();
        filmResult1.setProperties(film1);

        FilmResult filmResult2 = new FilmResult();
        filmResult2.setProperties(film2);

        List<FilmResult> allFilms = List.of(filmResult1, filmResult2);

        FilmResponse filmResponse = new FilmResponse();
        filmResponse.setResult(allFilms);
        return filmResponse;
    }

}