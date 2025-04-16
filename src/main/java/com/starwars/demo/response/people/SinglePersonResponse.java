package com.starwars.demo.response.people;

import com.starwars.demo.model.people.PersonDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SinglePersonResponse extends GenericResponse {

    private PersonDetail result;

}
