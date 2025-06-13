package com.KHH.novelsite.Bookmark.Repository;

import com.KHH.novelsite.Bookmark.Entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // 1. 특정 유저가 특정 소설을 북마크했는지 체크 (중복방지, 해제용)
    Optional<Bookmark> findByUser_UnoAndNovel_Nno(Long uno, Long nno);

    // 2. 특정 소설의 전체 북마크 수
    long countByNovel_Nno(Long nno);

    // 3. 특정 유저가 북마크한 모든 소설
    List<Bookmark> findByUser_Uno(Long uno);
}
