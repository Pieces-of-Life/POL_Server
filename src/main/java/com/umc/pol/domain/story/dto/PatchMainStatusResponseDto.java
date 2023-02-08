package com.umc.pol.domain.story.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class PatchMainStatusResponseDto {
    Boolean isPicked;

    @Builder
    public PatchMainStatusResponseDto(boolean isPicked) {
        this.isPicked = isPicked;
    }

}
