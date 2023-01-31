package com.umc.pol.domain.template.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TemplateResDto {
    private String questionTemplate;

    @Builder
    public TemplateResDto(String questionTemplate) {
        this.questionTemplate = questionTemplate;
    }
}
