package com.KHH.novelsite.novel.entity;

public enum Genre {
    FANTASY("판타지"),
    ROMANCE("로맨스"),
    ALT_HISTORY("대체역사"),
    DAILY("일상"),
    SF("SF"),
    MARTIAL_ARTS("무협"),
    MYSTERY("미스터리"),
    ETC("기타");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
