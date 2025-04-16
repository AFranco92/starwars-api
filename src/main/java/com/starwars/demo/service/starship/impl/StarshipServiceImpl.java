package com.starwars.demo.service.starship.impl;

import com.starwars.demo.exception.ExternalServiceException;
import com.starwars.demo.exception.NotFoundException;
import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.response.starship.StarshipResponse;
import com.starwars.demo.response.starship.SingleStarshipResponse;
import com.starwars.demo.response.starship.StarshipsSearchedResponse;
import com.starwars.demo.service.starship.IStarshipService;
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
public class StarshipServiceImpl implements IStarshipService {

    private final Logger logger = LoggerFactory.getLogger(StarshipServiceImpl.class);
    private final RestTemplate restTemplate;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.scheme}")
    private String scheme;

    @Value("${api.host}")
    private String host;

    @Value("${api.path}")
    private String path;

    public StarshipServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve starships paginated.
     *
     * @param pageable The pagination information
     * @return a page of starships
     */
    @Override
    public Page<Starship> findAll(Pageable pageable) {
        try {
            StarshipResponse starshipsResponse = restTemplate.getForObject(baseUrl + "/starships", StarshipResponse.class);
            if (starshipsResponse.getResults() == null) {
                return Page.empty();
            }
            List<Starship> allStarships = starshipsResponse.getResults();
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), allStarships.size());
            List<Starship> paged = allStarships.subList(start, end);
            return new PageImpl<>(paged, pageable, allStarships.size());
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch starships from external API", e);
        }
    }

    /**
     * Search a starship by its ID.
     *
     * @param id ID of the starship to search
     * @return the starship if found
     */
    @Override
    public SingleStarshipResponse findById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl+"/starships/" + id.toString(), SingleStarshipResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Starship with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch starship from external API", e);
        }
    }

    /**
     * Search a starship by its name.
     *
     * @param name Name of the starship to search
     * @return the starship if found
     */
    @Override
    public StarshipsSearchedResponse findByName(String name) {
        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme(scheme)
                .host(host)
                .path(path+"/starships")
                .queryParam("name", name)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(uri, StarshipsSearchedResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Starships with name " + name + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch starships from external API", e);
        }
    }

    /**
     * Search a starship by its id and name.
     *
     * @param id ID of the starship to search
     * @param name Name of the starship to search
     * @return the starship if found
     */
    @Override
    public SingleStarshipResponse findByIdAndName(Long id, String name) {
        try {
            SingleStarshipResponse starship = findById(id);
            if(starship.getResult().getProperties().getName().toLowerCase().contains(name)) {
                return starship;
            }
            else {
                throw new NotFoundException("No starship was found with ID " + id + " and name " + name + ".");
            }
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Starship with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch starship from external API", e);
        }
    }

}

