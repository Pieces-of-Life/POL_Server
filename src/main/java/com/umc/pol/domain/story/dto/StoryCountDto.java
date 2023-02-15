package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StoryCountDto {
    private int userStoryCnt;
    private int userPieceCnt;
    private List<ResponseStoryFilterDto> storyTags;

    @Builder
    public StoryCountDto(int userStoryCnt, int userPieceCnt, List<ResponseStoryFilterDto> storyTags) {
        this.userStoryCnt = userStoryCnt;
        this.userPieceCnt = userPieceCnt;
        this.storyTags = storyTags;
    }
}
