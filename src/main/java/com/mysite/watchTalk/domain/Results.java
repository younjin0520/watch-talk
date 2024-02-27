package com.mysite.watchTalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name = "results")
@Getter
public class Results {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TVSERIES_ID")
    private TVSeries tvSeries;  //fk

    private String backdropPath;
    private String firstAirDate;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> genreIds;

    private String name;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> originCountry;

    private String originalLanguage;
    private String originalName;
    @Column(columnDefinition = "TEXT")
    private String overview;
    private Double popularity;
    private String posterPath;
    private Integer voteAverage;
    private Integer voteCount;

    public void setTvSeries(TVSeries tvSeries) {
        if (this.tvSeries != null) {
            this.tvSeries.getResults().remove(this);
        }
        this.tvSeries = tvSeries;
        tvSeries.getResults().add(this);
    }
}
