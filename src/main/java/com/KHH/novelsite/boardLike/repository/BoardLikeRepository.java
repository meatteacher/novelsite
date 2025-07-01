package com.KHH.novelsite.boardLike.repository;

import com.KHH.novelsite.boardLike.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    // 1. 특정 유저가 특정 게시글에 좋아요를 눌렀는지 체크 (중복방지, 해제용)
    Optional<BoardLike> findByUser_UnoAndBoard_Boardno(Long uno, Long boardno);

    // 2. 특정 게시글의 좋아요 수
    long countByBoard_Boardno(Long boardno);

    // 3. 특정 게시글 삭제때 좋아요 삭제
    @Modifying
    @Query("DELETE FROM BoardLike bl WHERE bl.board.boardno = :boardno")
    void deleteByBoard_Boardno(Long boardno);
}
