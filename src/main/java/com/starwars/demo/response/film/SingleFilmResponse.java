package com.starwars.demo.response.film;

import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleFilmResponse extends GenericResponse {

    private FilmResult result;

}
