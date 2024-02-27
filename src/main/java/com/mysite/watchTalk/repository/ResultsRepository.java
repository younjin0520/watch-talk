package com.mysite.watchTalk.repository;

import com.mysite.watchTalk.domain.Results;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultsRepository extends JpaRepository<Results, Integer> {
}