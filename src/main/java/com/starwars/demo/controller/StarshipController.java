package com.starwars.demo.controller;

import com.starwars.demo.exception.ErrorResponse;
import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.response.starship.SingleStarshipResponse;
import com.starwars.demo.response.starship.StarshipsSearchedResponse;
import com.starwars.demo.service.starship.IStarshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/starships")
@AllArgsConstructor
public class StarshipController {

    private final IStarshipService service;

    /**
     * Retrieve starships paginated.
     *
     * @param pageable The pagination information
     * @return a page of starships
     */
    @Operation(summary = "Retrieve starships paginated.", description = "Return starships paginated.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Starships found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Starship.class))),
            @ApiResponse(responseCode = "404", description = "Starships not found",
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
    public Page<Starship> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    /**
     * Search a starship by its ID.
     *
     * @param id ID of the starship to search
     * @return the starship if found
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Starship found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleStarshipResponse.class))),
            @ApiResponse(responseCode = "404", description = "Starship not found",
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
    public SingleStarshipResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Search a starship by its name.
     *
     * @param name Name of the starship to search
     * @return the starship if found
     */
    @Operation(summary = "Search a starship by name.", description = "Returns a starship by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Starships found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleStarshipResponse.class))),
            @ApiResponse(responseCode = "404", description = "Starships not found",
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
    public StarshipsSearchedResponse findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    /**
     * Search a starship by its id and name.
     *
     * @param id ID of the starship to search
     * @param name Name of the starship to search
     * @return the starship if found
     */
    @Operation(summary = "Search a starship by id and name.", description = "Returns a starship by its id and name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Starship found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SingleStarshipResponse.class))),
            @ApiResponse(responseCode = "404", description = "Starship not found",
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
    public SingleStarshipResponse findByIdAndName(@RequestParam Long id, @RequestParam String name) {
        return service.findByIdAndName(id, name);
    }

}
