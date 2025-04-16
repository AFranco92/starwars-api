package com.starwars.demo.service.people;

import com.starwars.demo.model.people.Person;
import com.starwars.demo.response.people.PeopleSearchedResponse;
import com.starwars.demo.response.people.SinglePersonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPeopleService {

    Page<Person> findAll(Pageable pageable);
    SinglePersonResponse findById(Long id);
    PeopleSearchedResponse findByName(String name);
    SinglePersonResponse findByIdAndName(Long id, String name);

}
