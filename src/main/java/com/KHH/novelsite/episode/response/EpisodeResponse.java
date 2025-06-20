package com.KHH.novelsite.episode.response;

import com.KHH.novelsite.episode.entity.Episode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EpisodeResponse {
    private Long epno;
    private String title;
    private String content;
    private Long episodeno;
    private LocalDateTime createdAt;
    private Long viewCount;
    private Long likeCount;

    public EpisodeResponse(Episode episode, long likeCount) {
        this.epno = episode.getEpno();
        this.title = episode.getTitle();
        this.content = episode.getContent();
        this.episodeno = episode.getEpisodeno();
        this.createdAt = episode.getCreatedAt();
        this.viewCount = episode.getViewCount();
        this.likeCount = likeCount;
    }
}