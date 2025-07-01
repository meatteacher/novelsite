package com.KHH.novelsite.admin;

import com.KHH.novelsite.board.repository.BoardRepository;
import com.KHH.novelsite.boardComment.repository.BoardCommentRepository;
import com.KHH.novelsite.boardLike.repository.BoardLikeRepository;
import com.KHH.novelsite.epComment.repository.EpCommentRepository;
import com.KHH.novelsite.epLike.repository.EpLikeRepository;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final EpisodeRepository episodeRepository;
    private final BoardRepository boardRepository;
    private final EpCommentRepository epCommentRepository;
    private final EpLikeRepository epLikeRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardLikeRepository boardLikeRepository;

    @GetMapping("/manage")
    public String adminManage(Model model) {
        model.addAttribute("episodeList", episodeRepository.findAll());
        model.addAttribute("boardList", boardRepository.findAll());
        model.addAttribute("epCommentList", epCommentRepository.findAll());
        model.addAttribute("boardCommentList", boardCommentRepository.findAll());
        return "08.manage_page"; // 방금 작성한 뷰 파일
    }

    // 게시글 삭제
    @PostMapping("/board/delete")
    @Transactional
    public String deleteBoard(@RequestParam Long bno) {
        boardCommentRepository.deleteByBoard_Boardno(bno);
        boardLikeRepository.deleteByBoard_Boardno(bno);
        boardRepository.deleteById(bno);
        return "redirect:/admin/manage";
    }

    // 에피소드 삭제
    @PostMapping("/episode/delete")
    @Transactional
    public String deleteEpisode(@RequestParam Long epno) {
        epCommentRepository.deleteByEpisode_Epno(epno); // 1. 댓글 먼저 삭제
        epLikeRepository.deleteByEpisode_Epno(epno);    // 2. 좋아요 삭제
        episodeRepository.deleteById(epno);            // 3. 에피소드 삭제
        return "redirect:/admin/manage";
    }

    @PostMapping("/board-comment/delete")
    public String deleteBoardComment(@RequestParam Long cno) {
        boardCommentRepository.deleteById(cno);
        return "redirect:/admin/manage";
    }

    @PostMapping("/ep-comment/delete")
    public String deleteEpComment(@RequestParam Long cno) {
        epCommentRepository.deleteById(cno);
        return "redirect:/admin/manage";
    }
}
