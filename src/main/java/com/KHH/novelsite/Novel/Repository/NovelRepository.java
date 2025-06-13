package com.KHH.novelsite.Novel.Repository;

import com.KHH.novelsite.Novel.Entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    // 소설 제목으로 찾기 (중복 방지, 검색 등)
    Optional<Novel> findByTitle(String title);

    // 작성자(유저) 기준으로 소설 전체 조회 (내 소설 리스트 등)
    List<Novel> findByUserUno(Long uno);
}
