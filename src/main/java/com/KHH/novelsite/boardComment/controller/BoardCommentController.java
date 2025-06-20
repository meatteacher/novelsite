package com.KHH.novelsite.boardComment.controller;

import com.KHH.novelsite.boardComment.entity.BoardComment;
import com.KHH.novelsite.boardComment.repository.BoardCommentRepository;
import com.KHH.novelsite.boardComment.request.BoardCommentCreateRequest;
import com.KHH.novelsite.board.entity.Board;
import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.boardComment.request.BoardCommentUpdateRequest;
import com.KHH.novelsite.boardComment.response.BoardCommentResponse;
import com.KHH.novelsite.boardComment.service.BoardCommentService;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board-comments")
public class BoardCommentController {

    private final BoardCommentService commentService;

    @GetMapping("/me")
    @ResponseBody
    public String getMyNickname(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return (loginUser != null) ? loginUser.getNickname() : "GUEST";
    }

    @PostMapping("/{boardId}")
    public void create(@PathVariable Long boardId,
                       @RequestBody BoardCommentCreateRequest request,
                       HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        commentService.createComment(boardId, uno, request);
    }

    @GetMapping("/{boardId}")
    public List<BoardCommentResponse> getComments(@PathVariable Long boardId) {
        return commentService.getComments(boardId); // 변수명 수정
    }

    @PutMapping("/{commentId}")
    public void update(@PathVariable Long commentId,
                       @RequestBody BoardCommentUpdateRequest request,
                       HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) throw new RuntimeException("로그인 필요");
        commentService.updateComment(commentId, loginUser.getUno(), request);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId,
                       HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) throw new RuntimeException("로그인 필요");
        commentService.deleteComment(commentId, loginUser.getUno());
    }
}

