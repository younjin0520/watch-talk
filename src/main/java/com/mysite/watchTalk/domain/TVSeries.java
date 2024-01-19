package com.mysite.watchTalk.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class TVSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int totalResults;
    private int totalPages;
    @OneToMany
    private List<Results> results;
    private int page;
}