package com.mysite.springboard.repository;

import com.mysite.springboard.domain.Answer;
import com.mysite.springboard.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findByQuestion(Pageable pageable, Question question);
}