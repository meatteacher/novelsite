package com.KHH.novelsite.epComment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpCommentUpdateRequest {
    private Long epCommentNo;  // 수정할 댓글 번호
    private String content;    // 수정할 내용
}