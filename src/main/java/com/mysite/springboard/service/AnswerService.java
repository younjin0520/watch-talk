package com.mysite.springboard.service;

import com.mysite.springboard.DataNotFoundException;
import com.mysite.springboard.repository.AnswerRepository;
import com.mysite.springboard.domain.Answer;
import com.mysite.springboard.domain.Question;
import com.mysite.springboard.domain.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 답변 저장
    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    // 답변 조회
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    // 답변 수정
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    // 답변 삭제
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    // 추천인 저장
    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        int voteCount = answer.getVoter().size();
        answer.setVoterCount(voteCount);

        this.answerRepository.save(answer);
    }

    public Page<Answer> getAnswerList(int no, Question question, String sortBy) {
        List<Sort.Order> sort = new ArrayList<>();
        if (sortBy.equals("latest")) sort.add(Sort.Order.desc("createDate"));
        else sort.add(Sort.Order.desc("voterCount"));
        Pageable pageable = PageRequest.of(no, 10, Sort.by(sort));
        return this.answerRepository.findByQuestion(pageable, question);
    }
}
