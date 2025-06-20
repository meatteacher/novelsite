package com.KHH.novelsite.board.controller;

import com.KHH.novelsite.board.entity.Board;
import com.KHH.novelsite.board.request.BoardCreateRequest;
import com.KHH.novelsite.board.request.BoardUpdateRequest;
import com.KHH.novelsite.board.response.BoardResponse;
import com.KHH.novelsite.board.service.BoardService;
import com.KHH.novelsite.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // ← 이걸로 변경!
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    @ResponseBody // 🔄 이건 여전히 JSON 응답이 필요하니까 유지
    public List<BoardResponse> getBoardList() {
        return boardService.getAllBoards();
    }

    @PostMapping("/write")
    @ResponseBody
    public void writeBoard(@RequestBody BoardCreateRequest request, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        boardService.createBoard(request, loginUser);
    }

    @GetMapping("/{boardno}")
    @ResponseBody
    public BoardResponse getBoard(@PathVariable Long boardno) {
        return boardService.getBoardById(boardno);
    }

    @PutMapping("/{boardno}")
    @ResponseBody
    public void updateBoard(@PathVariable Long boardno,
                            @RequestBody BoardUpdateRequest request,
                            HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        boardService.updateBoard(boardno, request, loginUser);
    }

    @DeleteMapping("/{boardno}")
    @ResponseBody
    public void deleteBoard(@PathVariable Long boardno, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        boardService.deleteBoard(boardno, loginUser);
    }
}