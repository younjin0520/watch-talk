package com.mysite.watchTalk;

import com.mysite.watchTalk.init.TVSeriesFetcher;
import com.mysite.watchTalk.service.ResultsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WatchTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchTalkApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner initData(TVSeriesFetcher tvSeriesFetcher) {
		return args -> {
//			 초기화 코드 실행
			tvSeriesFetcher.saveToDB();
		};
	}
	 */
}
