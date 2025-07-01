package com.KHH.novelsite.boardComment.repository;

import com.KHH.novelsite.boardComment.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    // 1. 특정 게시글(board) 모든 댓글 (최신순)
    List<BoardComment> findByBoard_BoardnoOrderByCreatedAtDesc(Long boardno);

    // 2. 특정 유저가 쓴 댓글 전체 (마이페이지 등)
    List<BoardComment> findByUser_Uno(Long uno);

    // 3. 특정 게시글의 댓글 개수
    long countByBoard_Boardno(Long boardno);

    // 4. 게시글 삭제시 댓글도 삭제
    @Modifying
    @Query("DELETE FROM BoardComment bc WHERE bc.board.boardno = :boardno")
    void deleteByBoard_Boardno(Long boardno);
}
