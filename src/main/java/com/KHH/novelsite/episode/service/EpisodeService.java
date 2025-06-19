package com.KHH.novelsite.episode.service;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import com.KHH.novelsite.episode.request.EpisodeUpdateRequest;
import com.KHH.novelsite.novel.entity.Novel;
import com.KHH.novelsite.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.KHH.novelsite.user.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final NovelRepository novelRepository;

    public List<Episode> getEpisodesByNovel(Long nno) {
        return episodeRepository.findByNovel_NnoOrderByEpisodenoDesc(nno);
    }

    public Episode getEpisodeById(Long epno) {
        return episodeRepository.findById(epno)
                .orElseThrow(() -> new IllegalArgumentException("에피소드를 찾을 수 없습니다."));
    }

    public void createEpisode(Long nno, String title, String content) {
        Novel novel = novelRepository.findById(nno)
                .orElseThrow(() -> new IllegalArgumentException("소설을 찾을 수 없습니다."));

        Long nextEpisodeNo = episodeRepository.countByNovel(novel) + 1;

        Episode episode = new Episode();
        episode.setNovel(novel);
        episode.setTitle(title);
        episode.setContent(content);
        episode.setEpisodeno(nextEpisodeNo);
        episode.setViewCount(0L);

        episodeRepository.save(episode);
    }

    public Episode getPrevEpisode(Episode current) {
        return episodeRepository.findByNovel_NnoAndEpisodeno(
                current.getNovel().getNno(),
                current.getEpisodeno() - 1
        ).orElse(null);
    }

    public Episode getNextEpisode(Episode current) {
        return episodeRepository.findByNovel_NnoAndEpisodeno(
                current.getNovel().getNno(),
                current.getEpisodeno() + 1
        ).orElse(null);
    }

    @Transactional
    public void updateEpisode(Long epno, EpisodeUpdateRequest request, User loginUser) {
        Episode episode = episodeRepository.findById(epno)
                .orElseThrow(() -> new IllegalArgumentException("해당 에피소드 없음"));
        if (!episode.getNovel().getUser().getUno().equals(loginUser.getUno())) {
            throw new IllegalStateException("작성자만 수정할 수 있습니다.");
        }
        episode.setTitle(request.getTitle());
        episode.setContent(request.getContent());
    }

    @Transactional
    public void deleteEpisode(Long epno, User loginUser) {
        Episode episode = episodeRepository.findById(epno)
                .orElseThrow(() -> new IllegalArgumentException("해당 에피소드 없음"));
        if (!episode.getNovel().getUser().getUno().equals(loginUser.getUno())) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
        episodeRepository.delete(episode);
    }
}
