package com.starwars.demo.response.people;

import com.starwars.demo.model.people.Person;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PeopleResponse extends GenericResponse {

    private Integer totalRecords;
    private Integer totalPages;
    private String previous;
    private String next;
    private List<Person> results;

}
