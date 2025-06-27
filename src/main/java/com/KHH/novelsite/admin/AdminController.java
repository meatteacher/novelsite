package com.KHH.novelsite.admin;

import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.boardComment.repository.BoardCommentRepository;
import com.KHH.novelsite.epComment.repository.EpCommentRepository;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final EpisodeRepository episodeRepository;
    private final BoardRepository boardRepository;
    private final EpCommentRepository epCommentRepository;
    private final BoardCommentRepository boardCommentRepository;

    @GetMapping("/manage")
    public String adminManage(Model model) {
        model.addAttribute("episodeList", episodeRepository.findAll());
        model.addAttribute("boardList", boardRepository.findAll());
        model.addAttribute("epcommentList", epCommentRepository.findAll());
        model.addAttribute("boardcommentList", boardCommentRepository.findAll());
        return "08.manage_page"; // 방금 작성한 뷰 파일
    }
}
