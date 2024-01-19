package com.mysite.watchTalk;

import com.mysite.watchTalk.domain.TVSeries;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebClientUtil {
    WebClient client = WebClient.builder()
            .baseUrl("https://api.themoviedb.org")
            .defaultHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjI5Mjg0MGUyYmM3YWEwMDRjZWFiYjUzM2ZkZjJiYiIsInN1YiI6IjY1ODk2NmYzNjg4Y2QwNTg1MDg0ZDQzZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VRK6bE9d2UXcTt89uMplWkP7OM_pjBMVVauNtS5Pus4")
           // .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
            .build();

    // get 요청
    public Flux<TVSeries> findAll() {
        return client
                .get()
                .uri("/3/trending/tv/day?language=ko-KR")
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(TVSeries.class);
    }
}
