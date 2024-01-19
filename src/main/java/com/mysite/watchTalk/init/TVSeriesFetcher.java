package com.mysite.watchTalk.init;

import com.mysite.watchTalk.domain.TVSeries;
import com.mysite.watchTalk.repository.TVSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class TVSeriesFetcher {

    @Autowired
    private TVSeriesRepository tvSeriesRepository;
    WebClient client = WebClient.builder()
            .baseUrl("https://api.themoviedb.org")
            .defaultHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjI5Mjg0MGUyYmM3YWEwMDRjZWFiYjUzM2ZkZjJiYiIsInN1YiI6IjY1ODk2NmYzNjg4Y2QwNTg1MDg0ZDQzZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VRK6bE9d2UXcTt89uMplWkP7OM_pjBMVVauNtS5Pus4")
            // .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();

    // get 요청
    public void fetchAllDataAndSaveToDB() {
        Flux<TVSeries> tvSeriesFlux = client
                .get()
                .uri("/3/trending/tv/day?language=ko-KR")
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(TVSeries.class);

        tvSeriesFlux
                .doOnNext(tvSeriesRepository::save)
                .subscribe();
    }
}