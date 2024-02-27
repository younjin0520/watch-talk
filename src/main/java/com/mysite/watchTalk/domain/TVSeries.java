package com.mysite.watchTalk.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Entity
public class TVSeries {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalResults;
    private Integer totalPages;

    @OneToMany(mappedBy = "tvSeries")
    private List<Results> results;

    private Integer page;

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public List<Results> getResults() {
        return this.results;
    }
}