package com.KHH.novelsite.boardLike.service;

import com.KHH.novelsite.board.entity.Board;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.boardLike.entity.BoardLike;
import com.KHH.novelsite.boardLike.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void likeBoard(Long boardno, User loginUser) {
        boolean alreadyLiked = boardLikeRepository
                .findByUser_UnoAndBoard_Boardno(loginUser.getUno(), boardno)
                .isPresent();

        if (alreadyLiked) return;

        Board board = boardRepository.findById(boardno)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        BoardLike boardLike = new BoardLike();
        boardLike.setUser(loginUser);
        boardLike.setBoard(board);

        boardLikeRepository.save(boardLike);
    }

    public long countLikes(Long boardno) {
        return boardLikeRepository.countByBoard_Boardno(boardno);
    }
}