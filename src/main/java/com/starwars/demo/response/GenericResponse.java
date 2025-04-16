package com.starwars.demo.response;

import com.starwars.demo.model.generic.Social;
import com.starwars.demo.model.generic.Support;
import lombok.Data;

@Data
public class GenericResponse {

    private String message;
    private String apiVersion;
    private String timestamp;
    private Support support;
    private Social social;

}
