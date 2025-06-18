package com.KHH.novelsite.episode.repository;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

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

    // 5. 소설에 속한 에피소드 개수 세기
    Long countByNovel(Novel novel);

    // 6. 소설 내 에피소드 넘버 받아오기
    Optional<Episode> findByNovel_NnoAndEpisodeno(Long nno, Long episodeno);
}
