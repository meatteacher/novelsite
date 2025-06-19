package com.KHH.novelsite.epComment.controller;

import com.KHH.novelsite.epComment.entity.EpComment;
import com.KHH.novelsite.epComment.request.EpCommentCreateRequest;
import com.KHH.novelsite.epComment.request.EpCommentUpdateRequest;
import com.KHH.novelsite.epComment.response.EpCommentResponse;
import com.KHH.novelsite.epComment.service.EpCommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/episode/{epno}/comments")
public class EpCommentController {

    private final EpCommentService epCommentService;

    @PostMapping
    public EpComment createComment(@PathVariable Long epno,
                                   @RequestBody EpCommentCreateRequest request,
                                   HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        if (uno == null) throw new RuntimeException("로그인이 필요합니다.");

        return epCommentService.createComment(uno, epno, request);
    }

    @GetMapping
    public List<EpCommentResponse> getComments(@PathVariable Long epno) {
        return epCommentService.getComments(epno);
    }

    @PostMapping("/epComment/update")
    public String updateComment(@PathVariable Long epno,
                                @RequestBody EpCommentUpdateRequest request,
                                HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        if (uno == null) throw new RuntimeException("로그인이 필요합니다.");

        epCommentService.updateComment(uno, request);
        return "ok";
    }

    @PostMapping("/epComment/delete")
    public String deleteComment(@PathVariable Long epno,
                                @RequestParam Long epCommentNo,
                                HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        if (uno == null) throw new RuntimeException("로그인이 필요합니다.");

        epCommentService.deleteComment(uno, epCommentNo);
        return "ok";
    }

    @GetMapping("/me")
    public String getMyNickname(HttpSession session) {
        Long uno = (Long) session.getAttribute("uno");
        if (uno == null) return "GUEST";

        return epCommentService.getNicknameByUno(uno);
    }
}
