package com.mysite.watchTalk.service;

import com.mysite.watchTalk.DataNotFoundException;
import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.repository.ResultsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResultsService {
    private final ResultsRepository resultsRepository;

    public List<Results> getList() { return this.resultsRepository.findAll(); }

    public Results getResult(Integer id) {
        Optional<Results> tvSeries = this.resultsRepository.findById(id);

        if (tvSeries.isPresent()) {
            return tvSeries.get();
        } else {
            throw new DataNotFoundException("tvSeries not found");
        }
    }
}
