package com.mysite.springboard;

import com.mysite.springboard.service.AnswerService;
import com.mysite.springboard.domain.Question;
import com.mysite.springboard.repository.QuestionRepository;
import com.mysite.springboard.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerService answerService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.create(subject, content, null);
        }
        // answer test data 생성
        String testSubject = "Answer Test";
        Question question = this.questionService.create(testSubject, "답변 테스트용", null);

        for (int i = 0; i < 100; i++) {
            this.answerService.create(question, Integer.toString(i), null);
        }
    }
}
