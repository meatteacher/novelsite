package com.KHH.novelsite.novel.service;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import com.KHH.novelsite.novel.entity.Novel;
import com.KHH.novelsite.novel.repository.NovelRepository;
import com.KHH.novelsite.novel.request.NovelCreateRequest;
import com.KHH.novelsite.novel.response.NovelResponse;
import com.KHH.novelsite.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;
    private final EpisodeRepository episodeRepository;

    public void registerNovelWithFirstEpisode(NovelCreateRequest req, User user) {
        // 1. Novel 생성&저장
        Novel novel = new Novel();
        novel.setTitle(req.getTitle());
        novel.setDescription(req.getDescription());
        novel.setCoverimg(req.getCoverImgPath());
        novel.setGenre(req.getGenre());
        novel.setUser(user);

        novelRepository.save(novel);

        // 2. Episode(1화) 생성&저장
        Episode ep = new Episode();
        ep.setTitle(req.getFirstEpisodeTitle());
        ep.setContent(req.getFirstEpisodeContent());
        ep.setEpisodeno(1L);
        ep.setNovel(novel);

        episodeRepository.save(ep);
    }

    public String saveCoverImg(MultipartFile coverImg) {
        if (coverImg == null || coverImg.isEmpty()) return null;
        try {
            String fileName = System.currentTimeMillis() + "_" + coverImg.getOriginalFilename();
            Path savePath = Paths.get("src/main/resources/static/img/pyoji", fileName);
            Files.copy(coverImg.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
            return "/img/pyoji/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("표지 파일 저장 실패", e);
        }
    }

    // 내 소설 리스트 조회 예시
    public List<NovelResponse> getNovelsByUser(Long uno) {
        return novelRepository.findByUserUno(uno).stream()
                .map(NovelResponse::new)
                .toList(); // 또는 collect(Collectors.toList())
    }

    // 장르별 소설 리스트 조회 추가
    public List<Novel> getNovelsByGenre(com.KHH.novelsite.novel.entity.Genre genre) {
        return novelRepository.findByGenre(genre);
    }

    // 제목 중복 체크 예시
    public boolean isTitleDuplicate(String title) {
        return novelRepository.findByTitle(title).isPresent();
    }

    public Novel getNovelById(Long nno) {
        return novelRepository.findById(nno).orElse(null);
    }
}
