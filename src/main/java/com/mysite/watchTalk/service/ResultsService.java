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