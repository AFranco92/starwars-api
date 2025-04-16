package com.starwars.demo.controller;

import com.starwars.demo.exception.ErrorResponse;
import com.starwars.demo.model.film.FilmResult;
import com.starwars.demo.response.film.FilmResponse;
import com.starwars.demo.response.film.SingleFilmResponse;
import com.starwars.demo.service.film.IFilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final IFilmService service;

    /**
     * Retrieve films paginated.
     *
     * @param pageable The pagination information
     * @return a page of films
     */
    @Operation(summary = "Retrieve films paginated.", description = "Return films paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Films found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FilmResult.class))),
            @ApiResponse(responseCode = "404", description = "Films not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("")
    public Page<FilmResult> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Search a film by its ID.
     *
     * @param id ID of the film to search
     * @return the film if found
     */
    @Operation(summary = "Search a film by ID.", description = "Returns a film by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleFilmResponse.class))),
            @ApiResponse(responseCode = "404", description = "Film not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public SingleFilmResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Search a film by its full or non-full title.
     *
     * @param title Title of the film to search
     * @return the film if found
     */
    @Operation(summary = "Search a film by title.", description = "Returns a film by its title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Films found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FilmResponse.class))),
            @ApiResponse(responseCode = "404", description = "Films not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public FilmResponse findByTitle(@RequestParam String title) {
        return service.findByTitle(title);
    }

    /**
     * Search a film by its id and title
     *
     * @param id ID of the film to search
     * @param title Title of the film to search
     * @return the film if found
     */
    @Operation(summary = "Search a film by id and title.", description = "Returns a film by its id and title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleFilmResponse.class))),
            @ApiResponse(responseCode = "404", description = "Film not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "External service error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/filter")
    public SingleFilmResponse findByIdAndTitle(@RequestParam Long id, @RequestParam String title) {
        return service.findByIdAndTitle(id, title);
    }

}
