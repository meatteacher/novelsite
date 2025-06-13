package com.KHH.novelsite.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // 메인페이지 파일명이 00.main_page.html이라면
        return "00.main_page";
    }
}
