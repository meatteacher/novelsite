package com.KHH.novelsite.board.response;

import com.KHH.novelsite.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponse {
    private Long boardno;
    private String title;
    private String content;
    private Long viewCount;
    private String createdAt;
    private String nickname;

    public BoardResponse(Board board) {
        this.boardno = board.getBoardno();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
        this.createdAt = board.getCreatedAt().toString();
        this.nickname = board.getUser().getNickname(); // User 엔티티에 nickname 필드 필요
    }
}