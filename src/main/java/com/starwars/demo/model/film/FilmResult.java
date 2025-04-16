package com.starwars.demo.model.film;

import lombok.Data;

@Data
public class FilmResult {

    private Film properties;
    private String _id;
    private String uid;
    private String description;
    private int __v;

}
