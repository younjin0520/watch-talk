package com.mysite.watchTalk.repository;

import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.domain.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Results, Integer> {
    Page<Results> findAll(Pageable pageable);

    Page<Results> findAll(Specification<Results> spec, Pageable pageable);

    List<Results> findTop20ByOrderByPopularityDesc();
    List<Results> findTop20ByOrderByFirstAirDateDesc();
}