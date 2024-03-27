package com.mysite.watchTalk.controller;

import com.mysite.watchTalk.domain.Question;
import com.mysite.watchTalk.domain.Results;
import com.mysite.watchTalk.service.ResultsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final ResultsService resultsService;

    @GetMapping("/")
    public String root(Model model) {
        List<Results> resultsByPopularity = this.resultsService.getListByPopularity();
        List<Results> resultsByRecent = this.resultsService.getListByFirstAirDate();
        model.addAttribute("resultsByPopularity", resultsByPopularity);
        model.addAttribute("resultsByRecent", resultsByRecent);
        return "home";
    }

    @GetMapping("/search")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Results> paging = this.resultsService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "search_result";
    }
}
