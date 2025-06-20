package com.KHH.novelsite.epComment.repository;

import com.KHH.novelsite.epComment.entity.EpComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpCommentRepository extends JpaRepository<EpComment, Long> {
    // 1. 특정 에피소드의 댓글 전체 (최신순 정렬)
    List<EpComment> findByEpisode_EpnoOrderByCreatedAtDesc(Long epno);

    // 2. 특정 에피소드의 댓글 전체 (오래된 순 정렬)
    List<EpComment> findByEpisode_EpnoOrderByCreatedAtAsc(Long epno);

    // 3. 특정 유저가 단 댓글 전체
    List<EpComment> findByUser_Uno(Long uno);

    // 4. 특정 유저가 특정 에피소드에 단 댓글 전체
    List<EpComment> findByUser_UnoAndEpisode_Epno(Long uno, Long epno);

    // 5. 에피소드 삭제때 댓글도 삭제
    void deleteByEpisode_Epno(Long epno);
}
