package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QnaDto {

    private Long id;
    private String question;
    private Long tagCategoryId;
    private String answer;

    @Builder
    public QnaDto(Long id,
                  String question,
                  Long tagCategoryId,
                  String answer) {
        this.id = id;
        this.question = question;
        this.tagCategoryId = tagCategoryId;
        this.answer = answer;
    }
}