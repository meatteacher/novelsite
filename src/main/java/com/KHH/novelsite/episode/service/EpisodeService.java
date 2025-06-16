package com.KHH.novelsite.episode.service;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public List<Episode> getEpisodesByNovel(Long nno) {
        return episodeRepository.findByNovel_NnoOrderByEpisodenoAsc(nno);
    }

    public Episode getEpisodeById(Long epno) {
        return episodeRepository.findById(epno)
                .orElseThrow(() -> new IllegalArgumentException("에피소드를 찾을 수 없습니다."));
    }
}
