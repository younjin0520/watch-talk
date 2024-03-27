package com.mysite.watchTalk.service;

import com.mysite.watchTalk.DataNotFoundException;
import com.mysite.watchTalk.domain.Answer;
import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.domain.SiteUser;
import com.mysite.watchTalk.repository.ResultsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResultsService {
    private final ResultsRepository resultsRepository;

    public List<Results> getList() { return this.resultsRepository.findAll(); }

    public Page<Results> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
        Specification<Results> spec = search(kw);
        return this.resultsRepository.findAll(spec, pageable);
    }

    public List<Results> getListByPopularity() {
        return this.resultsRepository.findTop20ByOrderByPopularityDesc();
    }

    public List<Results> getListByFirstAirDate() {
        return this.resultsRepository.findTop20ByOrderByFirstAirDateDesc();
    }

    public Results getResult(Integer id) {
        Optional<Results> tvSeries = this.resultsRepository.findById(id);

        if (tvSeries.isPresent()) {
            return tvSeries.get();
        } else {
            throw new DataNotFoundException("tvSeries not found");
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteDuplicateResults() {
        // 중복된 name 값을 가진 Results 엔티티를 식별
        List<String> duplicateNames = entityManager.createQuery(
                        "SELECT r.name FROM Results r GROUP BY r.name HAVING COUNT(r.name) > 1", String.class)
                .getResultList();

        // 중복된 name 값을 가진 Results 엔티티의 Question 엔티티 레코드 삭제
        for (String name : duplicateNames) {
            entityManager.createQuery("DELETE FROM Question q WHERE q.results.name = :name")
                    .setParameter("name", name)
                    .executeUpdate();
        }

        // 중복된 name 값을 가진 Results 엔티티 레코드 중 하나를 남기고 나머지 삭제
        entityManager.createQuery("DELETE FROM Results r WHERE r.name IN :names AND r.id NOT IN (" +
                        "SELECT MIN(r2.id) FROM Results r2 WHERE r2.name = r.name GROUP BY r2.name)")
                .setParameter("names", duplicateNames)
                .executeUpdate();
    }

    // 검색
    private Specification<Results> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Results> r, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거
                return cb.or(cb.like(r.get("name"), "%" + kw + "%")); // 제목
            }
        };
    }
}