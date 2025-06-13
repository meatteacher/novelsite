package com.KHH.novelsite.Episode.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodeCreateRequest {
    private String title;
    private String content;
    private Long episodeno;
}
