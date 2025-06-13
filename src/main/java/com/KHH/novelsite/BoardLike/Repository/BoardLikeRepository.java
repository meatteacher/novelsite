package com.KHH.novelsite.BoardLike.Repository;

import com.KHH.novelsite.BoardLike.Entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {// 1. 특정 유저가 특정 게시글에 좋아요를 눌렀는지 체크 (중복방지, 해제용)
    Optional<BoardLike> findByUser_UnoAndBoard_Boardno(Long uno, Long boardno);

    // 2. 특정 게시글의 좋아요 수
    long countByBoard_Boardno(Long boardno);
}
