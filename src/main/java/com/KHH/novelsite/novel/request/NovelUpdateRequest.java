package com.KHH.novelsite.novel.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelUpdateRequest {
    private String title;
    private String description;
    private String coverImgPath;
}
