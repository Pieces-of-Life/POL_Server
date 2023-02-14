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
    private boolean isOpen;
    private boolean isMain;
    private String profileImgUrl;
    private String nickname;
    private Long writerId;
    private Long myId;


    @Builder
    public StoryCoverDto(Long id,
                         String title,
                         String description,
                         LocalDateTime date,
                         String color,
                         Long likeCnt,
                         boolean isLiked,
                         boolean isOpen,
                         boolean isMain,
                         String profileImgUrl,
                         String nickname,
                         Long writerId,
                         Long myId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.color = color;
        this.likeCnt = likeCnt;
        this.isLiked = isLiked;
        this.isOpen = isOpen;
        this.isMain = isMain;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.writerId = writerId;
        this.myId = myId;
    }
}