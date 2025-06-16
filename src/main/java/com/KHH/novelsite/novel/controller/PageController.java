package com.KHH.novelsite.novel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/article")
    public String articlePage() {
        return "01.article_page";
    }
}
