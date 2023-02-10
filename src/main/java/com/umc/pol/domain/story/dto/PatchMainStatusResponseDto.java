package com.umc.pol.domain.story.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class PatchMainStatusResponseDto {
    Boolean isMain;

    @Builder
    public PatchMainStatusResponseDto(boolean isMain) {
        this.isMain = isMain;
    }

}
