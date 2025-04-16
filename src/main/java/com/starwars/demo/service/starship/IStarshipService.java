package com.starwars.demo.service.starship;

import com.starwars.demo.model.starship.Starship;
import com.starwars.demo.response.starship.SingleStarshipResponse;
import com.starwars.demo.response.starship.StarshipsSearchedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStarshipService {

    Page<Starship> findAll(Pageable pageable);
    SingleStarshipResponse findById(Long id);
    StarshipsSearchedResponse findByName(String name);
    SingleStarshipResponse findByIdAndName(Long id, String name);

}
