package com.umc.pol.domain.story.dto;

import com.umc.pol.domain.story.entity.Qna;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class StorySpecDto {

    private
    StoryCoverDto story;
    private List<Qna> qnaList;

    @Builder
    public StorySpecDto(StoryCoverDto story, List<Qna> qnaList) {
        this.story = story;
        this.qnaList = qnaList;
    }
}