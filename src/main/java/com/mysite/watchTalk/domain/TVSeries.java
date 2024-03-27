package com.mysite.watchTalk.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Entity
public class TVSeries {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalResults;
    @Getter
    private Integer totalPages;

    @Getter
    @OneToMany(mappedBy = "tvSeries")
    private List<Results> results;

    private Integer page;
}