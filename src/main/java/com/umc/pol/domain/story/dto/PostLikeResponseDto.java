package com.umc.pol.domain.story.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class PostLikeResponseDto {
    Boolean isLiked;

    @Builder
    public PostLikeResponseDto(boolean isLiked) {
        this.isLiked = isLiked;
    }

}
