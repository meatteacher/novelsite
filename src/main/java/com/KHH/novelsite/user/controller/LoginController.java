package com.KHH.novelsite.user.controller;

import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute ("user", new User()); // 빈 User 객체 전달 (Thymeleaf 등에서 사용)
        return "04.loginhaera"; // templates/register.html 파일로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String pwd,
                        HttpServletResponse response,
                        HttpSession session,
                        Model model) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            model.addAttribute("msg", "이메일, 혹은 비밀번호가 일치하지 않습니다.");
            return "04.loginhaera";
        }

        User user =optionalUser.get();

        if(!bCryptPasswordEncoder.matches(pwd, user.getPwd())){
            model.addAttribute("msg", "이메일, 혹은 비밀번호가 일치하지 않습니다.");
            return "04.loginhaera";
        }

        session.setAttribute("loginUser", user);  // 세션에 저장 (원하는 키 이름 사용)
        session.setMaxInactiveInterval(60 * 60 * 24); // (선택) 세션 유지시간: 24시간

        return "redirect:/";
    }

}
