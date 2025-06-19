package com.KHH.novelsite.epLike.controller;

import com.KHH.novelsite.epLike.service.EpLikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/episode/like")
public class EpLikeController {

    private final EpLikeService epLikeService;

    // 좋아요 토글
    @PostMapping
    public String toggleLike(@RequestParam Long epno, HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        if (uno == null) return "로그인이 필요합니다";

        boolean liked = epLikeService.toggleLike(uno, epno);
        return liked ? "좋아요 등록됨" : "좋아요 취소됨";
    }

    // 좋아요 수 조회
    @GetMapping("/count")
    public long getLikeCount(@RequestParam Long epno) {
        return epLikeService.getLikeCount(epno);
    }
}
