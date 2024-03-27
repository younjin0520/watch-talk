package com.mysite.watchTalk.controller;

import com.mysite.watchTalk.domain.Answer;
import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.domain.SiteUser;
import com.mysite.watchTalk.form.AnswerForm;
import com.mysite.watchTalk.form.QuestionForm;
import com.mysite.watchTalk.service.AnswerService;
import com.mysite.watchTalk.service.QuestionService;
import com.mysite.watchTalk.service.ResultsService;
import com.mysite.watchTalk.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final ResultsService resultsService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);   // questionList라는 이름으로 값 저장
        model.addAttribute("kw", kw);
        return "question_list"; // question_list.html 반환
    }

    @GetMapping("/list/{id}")
    public String board(
            Model model,
            @PathVariable("id") Integer id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "searchKw", defaultValue = "") String searchKw
    ) {
        Results results = this.resultsService.getResult(id);
        Page<Question> paging = this.questionService.getQuestionList(searchKw, page, results);

        String imgUrl = "https://image.tmdb.org/t/p/w500" + results.getBackdropPath();

        model.addAttribute("id", id);
        model.addAttribute("results", results);
        model.addAttribute("imgUrl", imgUrl);
        model.addAttribute("paging", paging);
        model.addAttribute("searchKw", searchKw);
        return "post_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    public String contentQuestionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String contentQuestionCreate(
            @PathVariable("id") Integer id,
            @Valid QuestionForm questionForm,
            BindingResult bindingResult,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Results results = this.resultsService.getResult(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser, results);
        return "redirect:/question/list/{id}";
    }

    //상세페이지 매핑
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model,
                         @PathVariable("id") Integer id,
                         @RequestParam(value = "no", defaultValue = "0") int no,
                         @RequestParam(name = "sort", defaultValue = "latest") String sort,
                         AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        Page<Answer> paging = this.answerService.getAnswerList(no, question, sort);
        model.addAttribute("question", question);
        model.addAttribute("paging", paging);
        model.addAttribute("sort", sort);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        // 질문 작성자와 로그인 사용자가 동일한지 확인
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}
