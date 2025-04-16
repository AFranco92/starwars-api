package com.starwars.demo.response.starship;

import com.starwars.demo.model.starship.StarshipDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StarshipsSearchedResponse extends GenericResponse {

    private List<StarshipDetail> result;

}