package com.starwars.demo.response.people;

import com.starwars.demo.model.people.PersonDetail;
import com.starwars.demo.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PeopleSearchedResponse extends GenericResponse {

    private List<PersonDetail> result;

}
