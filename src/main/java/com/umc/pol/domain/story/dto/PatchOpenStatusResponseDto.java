package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PatchOpenStatusResponseDto {
    Boolean isOpened;

    @Builder
    public PatchOpenStatusResponseDto(boolean isOpened){
        this.isOpened = isOpened;
    }
}
