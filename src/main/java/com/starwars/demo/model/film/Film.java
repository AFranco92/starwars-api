package com.starwars.demo.model.film;

import lombok.Data;

import java.util.List;

@Data
public class Film {

    private String title;
    private int episodeId;
    private String openingCrawl;
    private String director;
    private String producer;
    private String releaseDate;
    private List<String> species;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> characters;
    private List<String> planets;
    private String url;
    private String created;
    private String edited;

}
