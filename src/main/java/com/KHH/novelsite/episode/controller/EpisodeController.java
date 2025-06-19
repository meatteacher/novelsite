package com.KHH.novelsite.episode.controller;

import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.request.EpisodeUpdateRequest;
import com.KHH.novelsite.episode.service.EpisodeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.KHH.novelsite.user.entity.User;

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

    @GetMapping("/episode/{epno}/detail")
    @ResponseBody
    public Episode getEpisodeDetail(@PathVariable Long epno) {
        return episodeService.getEpisodeById(epno);
    }

    @GetMapping("/viewer/{epno}")
    public String viewEpisode(@PathVariable Long epno, Model model, HttpSession session) {
        Episode episode = episodeService.getEpisodeById(epno);
        Episode prev = episodeService.getPrevEpisode(episode);
        Episode next = episodeService.getNextEpisode(episode);

        User loginUser = (User) session.getAttribute("loginUser");
        Long loginUserId = (loginUser != null) ? loginUser.getUno() : null;
        Long authorId = episode.getNovel().getUser().getUno();
        Long novelNno = episode.getNovel().getNno();

        model.addAttribute("episode", episode);
        model.addAttribute("prevEpno", prev != null ? prev.getEpno() : null);
        model.addAttribute("nextEpno", next != null ? next.getEpno() : null);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("authorId", authorId);
        model.addAttribute("novelNno", novelNno);

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

    @PutMapping("/episode/{epno}/edit")
    @ResponseBody
    public ResponseEntity<?> editEpisode(@PathVariable Long epno,
                                         @RequestBody EpisodeUpdateRequest request,
                                         HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        episodeService.updateEpisode(epno, request, loginUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/episode/{epno}/delete")
    @ResponseBody
    public ResponseEntity<?> deleteEpisode(@PathVariable Long epno, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        episodeService.deleteEpisode(epno, loginUser);
        return ResponseEntity.ok().build();
    }
}
