package com.mysite.watchTalk.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.domain.TVSeries;
import com.mysite.watchTalk.repository.ResultsRepository;
import com.mysite.watchTalk.repository.TVSeriesRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Transactional
@Component
public class TVSeriesFetcher {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjI5Mjg0MGUyYmM3YWEwMDRjZWFiYjUzM2ZkZjJiYiIsInN1YiI6IjY1ODk2NmYzNjg4Y2QwNTg1MDg0ZDQzZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VRK6bE9d2UXcTt89uMplWkP7OM_pjBMVVauNtS5Pus4";
    private static final String BASE_URL = "https://api.themoviedb.org";
    private static final String LANGUAGE = "ko-KR";
    private static final String COUNTRY = "KR";
    private int page = 1;

    @Autowired
    private TVSeriesRepository tvSeriesRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    public ResponseEntity<String> getKoreanTVSeries() {
        String url = BASE_URL + "/3/discover/tv";

        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Query parameters
        URI uri = URI.create(url + "?language=" + LANGUAGE + "&with_origin_country=" + COUNTRY);

        RequestEntity<Void> requestEntity
                = new RequestEntity(headers, HttpMethod.GET, uri);
        return restTemplate.exchange(requestEntity, String.class);
    }

    public void saveToDB() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = getKoreanTVSeries();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        TVSeries responseData = objectMapper.treeToValue(jsonNode, TVSeries.class);

        for (Results result : responseData.getResults()) {
            resultsRepository.save(result);
        }
    }
}