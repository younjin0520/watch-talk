package com.mysite.watchTalk.repository;

import com.mysite.watchTalk.domain.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVSeriesRepository extends JpaRepository<TVSeries, Integer> {
}