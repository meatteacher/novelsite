package com.KHH.novelsite.novel.response;

import com.KHH.novelsite.novel.entity.Genre;
import com.KHH.novelsite.novel.entity.Novel;
import lombok.Data;

@Data
public class NovelResponse {
    private Long nno;
    private String title;
    private String description;
    private String coverimg;
    private String writer;
    private Genre genre;

    public NovelResponse(Novel novel) {
        this.nno = novel.getNno();
        this.title = novel.getTitle();
        this.description = novel.getDescription();
        this.coverimg = novel.getCoverimg();  // 이게 JS에서 쓰는 coverimg
        this.writer = novel.getUser() != null ? novel.getUser().getNickname() : "작가정보없음";
        this.genre = novel.getGenre();
    }
}
