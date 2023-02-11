package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.umc.pol.domain.story.dto.request.QnaDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class StorySpecDto {

    private
    StoryCoverDto story;
    private List<QnaDto> qnaList;

    @Builder
    public StorySpecDto(StoryCoverDto story, List<QnaDto> qnaList) {
        this.story = story;
        this.qnaList = qnaList;
    }
}