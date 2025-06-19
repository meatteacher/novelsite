package com.KHH.novelsite.epLike.service;

import com.KHH.novelsite.epLike.entity.EpLike;
import com.KHH.novelsite.epLike.repository.EpLikeRepository;
import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpLikeService {

    private final EpLikeRepository epLikeRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    @Transactional
    public boolean toggleLike(Long uno, Long epno) {
        EpLike existing = epLikeRepository.findByUser_UnoAndEpisode_Epno(uno, epno).orElse(null);
        if (existing != null) {
            epLikeRepository.delete(existing);
            return false; // 좋아요 취소됨
        } else {
            User user = userRepository.findById(uno).orElseThrow();
            Episode episode = episodeRepository.findById(epno).orElseThrow();
            EpLike like = new EpLike();
            like.setUser(user);
            like.setEpisode(episode);
            epLikeRepository.save(like);
            return true; // 좋아요 등록됨
        }
    }

    public long getLikeCount(Long epno) {
        return epLikeRepository.countByEpisode_Epno(epno);
    }
}
