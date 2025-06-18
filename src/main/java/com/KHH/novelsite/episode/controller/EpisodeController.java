package com.KHH.novelsite.episode.controller;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    @GetMapping("/novel/{nno}/episodes")
    @ResponseBody
    public List<Episode> getEpisodes(@PathVariable Long nno) {
        return episodeService.getEpisodesByNovel(nno);
    }

    @GetMapping("/viewer/{epno}")
    public String viewEpisode(@PathVariable Long epno, Model model) {
        Episode episode = episodeService.getEpisodeById(epno);

        // 이전/다음 회차 가져오기
        Episode prev = episodeService.getPrevEpisode(episode);
        Episode next = episodeService.getNextEpisode(episode);

        model.addAttribute("episode", episode);
        model.addAttribute("prevEpno", prev != null ? prev.getEpno() : null);
        model.addAttribute("nextEpno", next != null ? next.getEpno() : null);
        return "02.viewer_system";
    }

    // 이어쓰기 폼 보여주기
    @GetMapping("/episode/write")
    public String writeForm(@RequestParam("nno") Long nno, Model model) {
        model.addAttribute("nno", nno);  // 뷰에 소설번호 넘겨줌
        return "episode_write";          // 폼 이름
    }

    // 이어쓰기 작성처리
    @PostMapping("/episode/write")
    public String writeSubmit(@RequestParam Long nno,
                              @RequestParam String title,
                              @RequestParam String content) {

        episodeService.createEpisode(nno, title, content);
        return "redirect:/article?nno=" + nno;  // 작성 후 아티클 페이지로 이동
    }
}
