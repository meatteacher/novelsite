package com.KHH.novelsite.board.repository;

import com.KHH.novelsite.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 1. 유저가 쓴 모든 게시글 (내 글 보기, 마이페이지 등)
    List<Board> findByUser_Uno(Long uno);

    // 2. 게시글 제목에 특정 키워드가 포함된 게시글
    List<Board> findByTitleContaining(String keyword);

    // 3. 게시글 본문(content)에 특정 키워드가 포함된 게시글
    List<Board> findByContentContaining(String keyword);

    // 4. 게시글 제목+본문에 모두 특정 키워드가 포함된 게시글 (AND 조건)
    List<Board> findByTitleContainingAndContentContaining(String tKeyword, String cKeyword);
}
