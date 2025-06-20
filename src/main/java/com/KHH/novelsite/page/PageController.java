package com.KHH.novelsite.page;

import com.KHH.novelsite.user.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/article")
    public String articlePage() {
        return "01.article_page";
    }

    @GetMapping("/05.gesipan")
    public String showBoardPage() {
        return "05.gesipan";
    }

    @GetMapping("/06.gesigul")
    public String showBoardDetail() {
        return "06.gesigul";
    }

    @GetMapping("/board/write")
    public String showBoardWritePage(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        return "07.write_board";
    }
}
