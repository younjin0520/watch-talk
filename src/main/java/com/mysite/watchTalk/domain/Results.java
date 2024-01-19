package com.mysite.watchTalk.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private List<String> originCountry;
    private int voteCount;
    private double voteAverage;
    private String firstAirDate;
    private double popularity;
    private List<Integer> genreIds;
    private String mediaType;
    private String posterPath;
    private String overview;
    private String originalName;
    private String originalLanguage;
    private String name;
    private String backdropPath;
    private boolean adult;
}
