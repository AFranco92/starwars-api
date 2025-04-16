package com.starwars.demo.response.film;

import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilmResponse extends GenericResponse {

    private List<FilmResult> result;

}
