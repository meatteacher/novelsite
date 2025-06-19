package com.KHH.novelsite.epComment.response;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class EpCommentResponse {
    private Long epcommentno;  // ✅ 프론트에 맞춰 이름 변경
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public EpCommentResponse(Long epcommentno, String content, String nickname, LocalDateTime createdAt) {
        this.epcommentno = epcommentno;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}

