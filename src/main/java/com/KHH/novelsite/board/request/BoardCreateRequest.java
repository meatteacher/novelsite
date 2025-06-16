package com.KHH.novelsite.board.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateRequest {
    private String title;
    private String content;
}
