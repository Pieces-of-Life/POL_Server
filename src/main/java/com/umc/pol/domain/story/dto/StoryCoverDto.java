package com.umc.pol.domain.story.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryCoverDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String color;
    private Long likeCnt;
    private boolean isLiked;
    private String profileImgUrl;
    private String nickname;

    @Builder
    public StoryCoverDto(Long id,
                         String title,
                         String description,
                         LocalDateTime date,
                         String color,
                         Long likeCnt,
                         boolean isLiked,
                         String profileImgUrl,
                         String nickname) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.color = color;
        this.likeCnt = likeCnt;
        this.isLiked = isLiked;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}