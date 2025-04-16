package com.starwars.demo.service.people.impl;

import com.starwars.demo.exception.ExternalServiceException;
import com.starwars.demo.exception.NotFoundException;
import com.starwars.demo.model.people.Person;
import com.starwars.demo.response.people.PeopleResponse;
import com.starwars.demo.response.people.PeopleSearchedResponse;
import com.starwars.demo.response.people.SinglePersonResponse;
import com.starwars.demo.service.people.IPeopleService;
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
public class PeopleServiceImpl implements IPeopleService {

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

    public PeopleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieve people paginated.
     *
     * @param pageable The pagination information
     * @return a page of people
     */
    @Override
    public Page<Person> findAll(Pageable pageable) {
         try {
             PeopleResponse peopleResponse = restTemplate.getForObject(baseUrl + "/people", PeopleResponse.class);
             if (peopleResponse.getResults() == null) {
                 return Page.empty();
             }
             List<Person> allPeople = peopleResponse.getResults();
             int start = (int) pageable.getOffset();
             int end = Math.min(start + pageable.getPageSize(), allPeople.size());
             List<Person> paged = allPeople.subList(start, end);
             return new PageImpl<>(paged, pageable, allPeople.size());
         } catch (RestClientException e) {
             logger.error(e.getMessage());
             throw new ExternalServiceException("Failed to fetch people from external API", e);
         }
    }

    /**
     * Search a person by its ID.
     *
     * @param id ID of the person to search
     * @return the person if found
     */
    @Override
    public SinglePersonResponse findById(Long id) {
        try {
            return restTemplate.getForObject(baseUrl+"/people/" + id.toString(), SinglePersonResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Person with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch person from external API", e);
        }
    }

    /**
     * Search a person by its name.
     *
     * @param name Name of the person to search
     * @return the person if found
     */
    @Override
    public PeopleSearchedResponse findByName(String name) {
        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme(scheme)
                .host(host)
                .path(path+"/people")
                .queryParam("name", name)
                .build()
                .encode()
                .toUri();
        try {
            return restTemplate.getForObject(uri, PeopleSearchedResponse.class);
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("People with name " + name + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch people from external API", e);
        }
    }

    /**
     * Search a person by its id and name.
     *
     * @param id ID of the person to search
     * @param name Name of the person to search
     * @return the person if found
     */
    @Override
    public SinglePersonResponse findByIdAndName(Long id, String name) {
        try {
            SinglePersonResponse person = findById(id);
            if(person.getResult().getProperties().getName().toLowerCase().contains(name)) {
                return person;
            }
            else {
                throw new NotFoundException("No person was found with ID " + id + " and name " + name + ".");
            }
        } catch(HttpClientErrorException.NotFound e) {
            logger.error(e.getMessage());
            throw new NotFoundException("Person with ID " + id + " not found.");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
            throw new ExternalServiceException("Failed to fetch person from external API", e);
        }
    }

}
