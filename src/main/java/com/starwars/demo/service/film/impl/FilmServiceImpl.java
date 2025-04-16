package com.starwars.demo.service.film.impl;

import com.starwars.demo.exception.ExternalServiceException;
import com.starwars.demo.exception.NotFoundException;
import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.film.FilmResponse;
import com.starwars.demo.response.film.SingleFilmResponse;
import com.starwars.demo.service.film.IFilmService;
import com.starwars.demo.service.people.impl.PeopleServiceImpl;
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
import java.util.NoSuchElementException;

@Service
public class FilmServiceImpl implements IFilmService {

    private final Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);
    private final RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.scheme}")
    private String scheme;

    @Value("${api.host}")
    private String host;

    @Value("${api.path}")
    private String path;

    public FilmServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve films paginated.
     *
     * @param pageable The pagination information
     * @return a page of films
     */
    @Override
    public Page<FilmResult> findAll(Pageable pageable) {
        try{
            FilmResponse filmResponse = restTemplate.getForObject(baseUrl+"/films", FilmResponse.class);
            if (filmResponse.getResult() == null) {
                return Page.empty();
            }
            List<FilmResult> allFilms = filmResponse.getResult();
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), allFilms.size());
            List<FilmResult> pagedFilms = allFilms.subList(start, end);
            return new PageImpl<>(pagedFilms, pageable, allFilms.size());
        }  catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch films from external API", e);
        }
    }

    /**
     * Search a film by its ID.
     *
     * @param id ID of the film to search
     * @return the film if found
     */
    @Override
    public SingleFilmResponse findById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/films/" + id.toString(), SingleFilmResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Film with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch film from external API", e);
        }
    }

    /**
     * Search a film by its full or non-full title.
     *
     * @param title Title of the film to search
     * @return the film if found
     */
    @Override
    public FilmResponse findByTitle(String title) {
        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme(scheme)
                .host(host)
                .path(path+"/films")
                .queryParam("title", title)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(uri, FilmResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Films with title " + title + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch films from external API", e);
        }
    }

    /**
     * Search a film by its id and title
     *
     * @param id ID of the film to search
     * @param title Title of the film to search
     * @return the film if found
     */
    @Override
    public SingleFilmResponse findByIdAndTitle(Long id, String title) {
        try {
            SingleFilmResponse film = findById(id);
            if (film.getResult().getProperties().getTitle().toLowerCase().contains(title.toLowerCase())) {
                return film;
            } else {
                throw new NoSuchElementException("No film was found with ID " + id + " and title " + title + ".");
            }
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Film with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch film from external API", e);
        }
    }

}