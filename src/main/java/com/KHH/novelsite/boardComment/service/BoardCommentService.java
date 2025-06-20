// BoardCommentService.java
package com.KHH.novelsite.boardComment.service;

import com.KHH.novelsite.board.entity.Board;
import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.boardComment.entity.BoardComment;
import com.KHH.novelsite.boardComment.repository.BoardCommentRepository;
import com.KHH.novelsite.boardComment.request.BoardCommentCreateRequest;
import com.KHH.novelsite.boardComment.request.BoardCommentUpdateRequest;
import com.KHH.novelsite.boardComment.response.BoardCommentResponse;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository commentRepo;
    private final UserRepository userRepo;
    private final BoardRepository boardRepo;

    @Transactional
    public void createComment(Long boardId, Long uno, BoardCommentCreateRequest request) {
        User user = userRepo.findById(uno).orElseThrow();
        Board board = boardRepo.findById(boardId).orElseThrow();

        BoardComment comment = new BoardComment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setBoard(board);

        commentRepo.save(comment);
    }

    public List<BoardCommentResponse> getComments(Long boardId) {
        return commentRepo.findByBoard_BoardnoOrderByCreatedAtDesc(boardId).stream()
                .map(BoardCommentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long commentId, Long uno, BoardCommentUpdateRequest request) {
        BoardComment comment = commentRepo.findById(commentId).orElseThrow();
        if (!comment.getUser().getUno().equals(uno)) {
            throw new RuntimeException("수정 권한이 없습니다");
        }
        comment.setContent(request.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, Long uno) {
        BoardComment comment = commentRepo.findById(commentId).orElseThrow();
        if (!comment.getUser().getUno().equals(uno)) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }
        commentRepo.delete(comment);
    }
}
