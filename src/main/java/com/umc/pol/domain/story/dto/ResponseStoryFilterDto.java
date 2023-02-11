package com.umc.pol.domain.story.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ResponseStoryFilterDto {
    private String storyTag;
    private List<ResponseStoryDto> stories;

    @QueryProjection
    @Builder
    public ResponseStoryFilterDto(String storyTag, List<ResponseStoryDto> stories) {
        this.storyTag = storyTag;
        this.stories = stories;
    }
}
