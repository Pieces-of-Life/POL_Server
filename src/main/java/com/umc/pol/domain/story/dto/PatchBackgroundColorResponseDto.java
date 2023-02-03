package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PatchBackgroundColorResponseDto {
    private String color;


    @Builder
    public PatchBackgroundColorResponseDto(String color) {
        this.color = color;
    }
}
