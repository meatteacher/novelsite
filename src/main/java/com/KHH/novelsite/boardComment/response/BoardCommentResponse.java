package com.KHH.novelsite.boardComment.response;

import com.KHH.novelsite.boardComment.entity.BoardComment;
import lombok.Getter;

@Getter
public class BoardCommentResponse {
    private Long id;         // 댓글 ID
    private Long uno;        // 댓글 작성자
    private String content;
    private String nickname;
    private String createdAt;

    public BoardCommentResponse(BoardComment entity) {
        this.id = entity.getBoardcommentno();
        this.uno = entity.getUser().getUno(); // ✔ 프론트에서 작성자 판별용
        this.content = entity.getContent();
        this.nickname = entity.getUser().getNickname();
        this.createdAt = entity.getCreatedAt().toString();
    }
}
