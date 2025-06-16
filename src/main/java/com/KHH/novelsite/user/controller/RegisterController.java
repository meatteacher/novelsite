package com.KHH.novelsite.user.controller;

import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    // 1. 회원가입 폼 화면 보여주기 (GET)
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute ("user", new User()); // 빈 User 객체 전달 (Thymeleaf 등에서 사용)
        return "03.joinHelldiver"; // templates/register.html 파일로 이동
    }

    // 2. 회원가입 처리 (POST)
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
            // 회원가입 성공시 → 로그인 페이지로 이동 (또는 바로 로그인/메인으로)
            model.addAttribute("msg", "회원가입이 성공적으로 완료되었습니다! 로그인 해주세요.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            // 회원가입 실패(예: 중복 이메일) 시
            model.addAttribute("error", e.getMessage());
            return "03.joinHelldiver"; // 다시 회원가입 폼 보여줌 (에러 메시지 포함)
        }
    }

}
