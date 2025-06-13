package com.KHH.novelsite.Episode.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodeUpdateRequest {
    private String title;
    private String content;
    private Long episodeno;
}
