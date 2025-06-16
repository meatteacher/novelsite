package com.KHH.novelsite.novel.response;

import com.KHH.novelsite.novel.entity.Genre;

public class GenreResponse {
    private String code;
    private String displayName;

    public GenreResponse(Genre genre) {
        this.code = genre.name();  // Enum의 name 값 (ex: FANTASY)
        this.displayName = genre.getDisplayName();  // Enum의 한글명
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
