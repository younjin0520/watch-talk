package com.mysite.springboard;

import com.mysite.springboard.repository.QuestionRepository;
import com.mysite.springboard.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringboardApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		// 데이터 조회
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());

		// 데이터 수정 - UPDATE문 실행됨
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());	// 값이 true인지 테스트
		Question q2 = oq.get();
		q2.setSubject("수정된 제목");
		this.questionRepository.save(q2);

		// 데이터 삭제 - delete문 실행됨
		assertEquals(6, this.questionRepository.count());
		Optional<Question> oq2 = this.questionRepository.findById(1);
		assertTrue(oq2.isPresent());
		Question q3 = oq2.get();
		this.questionRepository.delete(q3);
		assertEquals(5, this.questionRepository.count());
	}

}
