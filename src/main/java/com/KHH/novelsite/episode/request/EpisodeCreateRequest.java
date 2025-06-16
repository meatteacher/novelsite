package com.KHH.novelsite.episode.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodeCreateRequest {
    private String title;
    private String content;
    private Long episodeno;
}
