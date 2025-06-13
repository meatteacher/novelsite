package com.KHH.novelsite.Episode.Repository;

import com.KHH.novelsite.Episode.Entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    // 1. 특정 단어가 content에 포함된 에피소드 전체 찾기
    List<Episode> findByContentContaining(String keyword);

    // 2. 특정 소설(novel_id)에서 특정 단어가 content에 포함된 에피소드 찾기
    List<Episode> findByNovel_NnoAndContentContaining(Long nno, String keyword);

    // 3. 특정 소설의 모든 에피소드 불러오기 (에피소드 번호 순 정렬)
    List<Episode> findByNovel_NnoOrderByEpisodenoAsc(Long nno);

    // 3.5 특정 소설의 모든 에피소드 불러오기 (에피소드 번호 역순 정렬)
    List<Episode> findByNovel_NnoOrderByEpisodenoDesc(Long nno);

    // 4. 제목에 특정 단어가 포함된 에피소드 찾기
    List<Episode> findByTitleContaining(String keyword);
}
