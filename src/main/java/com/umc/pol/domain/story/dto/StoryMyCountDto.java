package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StoryMyCountDto {
    private int userStoryCnt;
    private int userPieceCnt;
    private List<ResponseMyStoryDto> stories;

    @Builder
    public StoryMyCountDto(int userStoryCnt, int userPieceCnt, List<ResponseMyStoryDto> stories) {
        this.userStoryCnt = userStoryCnt;
        this.userPieceCnt = userPieceCnt;
        this.stories = stories;
    }
}
