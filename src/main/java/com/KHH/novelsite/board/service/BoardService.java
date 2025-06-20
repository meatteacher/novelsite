package com.KHH.novelsite.board.service;

import com.KHH.novelsite.board.request.BoardUpdateRequest;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.board.entity.Board;
import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.board.request.BoardCreateRequest;
import com.KHH.novelsite.board.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    public void createBoard(BoardCreateRequest request, User user) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setUser(user);  // 로그인 유저 저장
        boardRepository.save(board);
    }

    public BoardResponse getBoardById(Long boardno) {
        Board board = boardRepository.findById(boardno)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
        return new BoardResponse(board);
    }

    public void updateBoard(Long boardno, BoardUpdateRequest request, User loginUser) {
        Board board = boardRepository.findById(boardno)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        // 작성자 확인
        if (!board.getUser().getUno().equals(loginUser.getUno())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardno, User loginUser) {
        Board board = boardRepository.findById(boardno)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        // 작성자 본인인지 확인
        if (!board.getUser().getUno().equals(loginUser.getUno())) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }
}
