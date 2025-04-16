package com.starwars.demo.service.film;

import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.film.FilmResponse;
import com.starwars.demo.response.film.SingleFilmResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmService {

    Page<FilmResult> findAll(Pageable pageable);
    SingleFilmResponse findById(Long id);
    FilmResponse findByTitle(String name);
    SingleFilmResponse findByIdAndTitle(Long id, String name);

}
