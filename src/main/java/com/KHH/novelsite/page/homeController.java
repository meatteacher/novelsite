package com.KHH.novelsite.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {
    @GetMapping("/")
    public String home() {
        // 메인페이지 파일명이 00.main_page.html이라면
        return "00.main_page";
    }
}
