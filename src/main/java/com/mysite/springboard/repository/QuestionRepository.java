package com.mysite.springboard.repository;

import com.mysite.springboard.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository를 상속할 때는 제네릭스 타입으로 <repository 대상이 되는 엔티티 타입, 해당 엔티티의 Pk 타입> 지정
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); // 특정 문자열이 포함된 데이터 조회
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
