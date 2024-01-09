package com.mysite.watchTalk.repository;

import com.mysite.watchTalk.domain.Answer;
import com.mysite.watchTalk.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer> findByQuestion(Pageable pageable, Question question);
}