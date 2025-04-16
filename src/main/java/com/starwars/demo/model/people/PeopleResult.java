package com.starwars.demo.model.people;

import com.starwars.demo.model.generic.Social;
import com.starwars.demo.model.generic.Support;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PeopleResult {

    private String message;
    private int totalRecords;
    private int totalPages;
    private String previous;
    private String next;
    private List<Person> results;
    private String apiVersion;
    private ZonedDateTime timestamp;
    private Support support;
    private Social social;

}
