package com.starwars.demo.controller;

import com.starwars.demo.exception.ErrorResponse;
import com.starwars.demo.model.people.Person;
import com.starwars.demo.response.people.PeopleSearchedResponse;
import com.starwars.demo.response.people.SinglePersonResponse;
import com.starwars.demo.service.people.IPeopleService;
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
@RequestMapping("/people")
@AllArgsConstructor
public class PeopleController {

    private final IPeopleService service;

    /**
     * Retrieve people paginated.
     *
     * @param pageable The pagination information
     * @return a page of people
     */
    @Operation(summary = "Retrieve people paginated.", description = "Return people paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "People found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class))),
            @ApiResponse(responseCode = "404", description = "People not found",
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
    public Page<Person> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Search a person by its ID.
     *
     * @param id ID of the person to search
     * @return the person if found
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SinglePersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found",
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
    public SinglePersonResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Search a person by its name.
     *
     * @param name Name of the person to search
     * @return the person if found
     */
    @Operation(summary = "Search a person by name.", description = "Returns a person by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "People found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SinglePersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "People not found",
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
    public PeopleSearchedResponse findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    /**
     * Search a person by its id and name.
     *
     * @param id ID of the person to search
     * @param name Name of the person to search
     * @return the person if found
     */
    @Operation(summary = "Search a person by id and name.", description = "Returns a person by its id and name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SinglePersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Person not found",
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
    public SinglePersonResponse findByIdAndName(@RequestParam Long id, @RequestParam String name) {
        return service.findByIdAndName(id, name);
    }

}
