package com.KHH.novelsite.epLike.repository;

import com.KHH.novelsite.epLike.entity.EpLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpLikeRepository extends JpaRepository<EpLike, Long> {
    // 1. 특정 유저가 특정 에피소드에 좋아요를 눌렀는지 체크 (중복방지용)
    Optional<EpLike> findByUser_UnoAndEpisode_Epno(Long uno, Long epno);

    // 2. 특정 에피소드의 좋아요 수
    long countByEpisode_Epno(Long epno);

    // 3. 에피소드 삭제때 좋아요도 삭제
    void deleteByEpisode_Epno(Long epno);
}
