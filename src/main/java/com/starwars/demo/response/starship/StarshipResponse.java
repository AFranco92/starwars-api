package com.starwars.demo.response.starship;

import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StarshipResponse extends GenericResponse {

    private int totalRecords;
    private int totalPages;
    private String previous;
    private String next;
    private List<Starship> results;

}