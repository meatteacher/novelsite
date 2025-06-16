package com.KHH.novelsite.episode.controller;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        model.addAttribute("episode", episode);
        return "02.viewer_system";
    }
}
