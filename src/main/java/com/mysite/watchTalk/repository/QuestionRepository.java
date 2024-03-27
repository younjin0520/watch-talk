package com.mysite.watchTalk.repository;

import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.domain.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository를 상속할 때는 제네릭스 타입으로 <repository 대상이 되는 엔티티 타입, 해당 엔티티의 Pk 타입> 지정
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}

