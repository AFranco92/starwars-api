package com.starwars.demo.response.starship;

import com.starwars.demo.model.starship.StarshipDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SingleStarshipResponse extends GenericResponse {

    private StarshipDetail result;

}