package com.umc.pol.domain.story.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ResponseStoryFilterDto {
    private String tag;
    private List<ResponseStoryDto> stories;

    @QueryProjection
    @Builder
    public ResponseStoryFilterDto(String tag, List<ResponseStoryDto> stories) {
        this.tag = tag;
        this.stories = stories;
    }
}
