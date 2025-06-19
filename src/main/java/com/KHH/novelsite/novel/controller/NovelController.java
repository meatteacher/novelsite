package com.KHH.novelsite.novel.controller;

import com.KHH.novelsite.novel.entity.Genre;
import com.KHH.novelsite.novel.entity.Novel;
import com.KHH.novelsite.novel.response.NovelResponse;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.novel.service.NovelService;
import com.KHH.novelsite.novel.request.NovelCreateRequest;
import com.KHH.novelsite.novel.response.GenreResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/novel")
@RequiredArgsConstructor
public class NovelController {
    private final NovelService novelService;

    @ResponseBody
    @GetMapping("/mylist")
    public List<NovelResponse> getMyNovels(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return List.of();
        return novelService.getNovelsByUser(loginUser.getUno());
    }

    @ResponseBody
    @GetMapping("/genre/{genre}")
    public List<NovelResponse> getNovelsByGenre(@PathVariable("genre") Genre genre) {
        List<Novel> novels = novelService.getNovelsByGenre(genre);
        return novels.stream().map(NovelResponse::new).toList();
    }

    @ResponseBody
    @GetMapping("/genre-list")
    public List<GenreResponse> getGenreList() {
        return Arrays.stream(Genre.values())
                .map(GenreResponse::new)
                .toList();
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<?> registerNovel(
            @ModelAttribute NovelCreateRequest request,
            @RequestParam(value = "coverImg", required = false) MultipartFile coverImg,
            @RequestParam(value = "coverSelect", required = false) String coverSelect,
            HttpSession session
    ) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return ResponseEntity.status(401).body("로그인 필요");

        String coverImgPath = null;
        if (coverImg != null && !coverImg.isEmpty()) {
            coverImgPath = novelService.saveCoverImg(coverImg);
        } else if (coverSelect != null && !coverSelect.isEmpty()) {
            coverImgPath = "/img/pyoji/" + coverSelect;
        } else {
            coverImgPath = "/img/pyoji/default.png";
        }
        // 여기서만 set 해줘야 함!
        request.setCoverImgPath(coverImgPath);

        if (novelService.isTitleDuplicate(request.getTitle())) {
            return ResponseEntity.badRequest().body("이미 존재하는 제목입니다.");
        }

        novelService.registerNovelWithFirstEpisode(request, loginUser);
        return ResponseEntity.ok("등록 완료");
    }

    @ResponseBody
    @GetMapping("/detail")
    public ResponseEntity<NovelResponse> getNovelDetail(@RequestParam("nno") Long nno) {
        Novel novel = novelService.getNovelById(nno);
        if (novel == null) return ResponseEntity.notFound().build();

        NovelResponse response = new NovelResponse(novel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/register-form")
    public String showRegisterForm(Model model) {
        model.addAttribute("genres", Genre.values());
        return "novelRegister";  // 현재 이 페이지 템플릿 이름
    }

}

