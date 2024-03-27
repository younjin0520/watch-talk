package com.mysite.watchTalk;

import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.repository.QuestionRepository;
import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.service.AnswerService;
import com.mysite.watchTalk.service.QuestionService;
import com.mysite.watchTalk.service.ResultsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class WatchTalkApplicationTests {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ResultsService resultsRepository;
	@Autowired
	private AnswerService answerService;

	@Test
	void testJpa() {
		Results results = this.resultsRepository.getResult(4);

		for (int i = 1; i <= 300; i++) {
			String subject = String.format("도배를 해보자:[%03d]", i);
			String content = "테스트용 도배입니다.";
			this.questionService.create(subject, content, null, results);
		}

		// answer test
		Question question = this.questionService.getQuestion(306);

		for (int i = 0; i < 100; i++) {
			this.answerService.create(question, Integer.toString(i), null);
		}
	}
}
