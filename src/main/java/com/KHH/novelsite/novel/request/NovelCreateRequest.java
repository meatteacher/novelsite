package com.KHH.novelsite.novel.request;

import com.KHH.novelsite.novel.entity.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovelCreateRequest {
    private String title;
    private String description;
    private String coverImgPath;
    private Genre genre;
    private String firstEpisodeTitle;
    private String firstEpisodeContent;
}