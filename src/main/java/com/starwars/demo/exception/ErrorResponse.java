package com.starwars.demo.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Timestamp of the error", example = "2025-04-15T23:45:00.123Z")
    private Instant timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Short description of the HTTP error", example = "Not Found")
    private String error;

    @Schema(description = "Detailed message of the error", example = "Person with ID 99 not found.")
    private String message;

    @Schema(description = "Path of the endpoint that caused the error", example = "/people/99")
    private String path;

}