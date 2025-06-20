package com.KHH.novelsite.boardLike.controller;

import com.KHH.novelsite.boardLike.repository.BoardLikeRepository;
import com.KHH.novelsite.boardLike.service.BoardLikeService;
import com.KHH.novelsite.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board-like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
    private final BoardLikeRepository boardLikeRepository;

    @PostMapping("/{boardno}")
    public void like(@PathVariable Long boardno, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        boardLikeService.likeBoard(boardno, loginUser);
    }
    // 좋아요 수 카운트용
    @GetMapping("/count/{boardno}")
    public long getLikeCount(@PathVariable Long boardno) {
        return boardLikeRepository.countByBoard_Boardno(boardno);
    }
}
