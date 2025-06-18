package com.KHH.novelsite.novel.repository;

import com.KHH.novelsite.novel.entity.Genre;
import com.KHH.novelsite.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    // 소설 제목으로 찾기 (중복 방지, 검색 등)
    Optional<Novel> findByTitle(String title);

    // 작성자(유저) 기준으로 소설 전체 조회 (내 소설 리스트 등)
    List<Novel> findByUserUno(Long uno);

    // 장르 기준으로 소설 리스트 조회
    List<Novel> findByGenre(Genre genre);

    // 소설이 존재하는지 확인
    Optional<Novel> findById(Long id);
}
