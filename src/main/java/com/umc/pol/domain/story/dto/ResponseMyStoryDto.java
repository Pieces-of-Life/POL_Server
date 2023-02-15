package com.umc.pol.domain.story.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.umc.pol.domain.story.entity.Story;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ResponseMyStoryDto {

    private long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String backgroundColor;
    private Long userId;
    private String profileImgUrl;
    private String nickname;


    @QueryProjection
    @Builder
    public ResponseMyStoryDto(Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.description = story.getDescription();
        this.date = story.getCreatedAt();
        this.backgroundColor = story.getColor();
        this.userId = story.getUser().getId();
        this.profileImgUrl = story.getUser().getProfileImg();
        this.nickname = story.getUser().getNickname();
    }
}
