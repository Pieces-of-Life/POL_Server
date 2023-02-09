package com.umc.pol.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class MypageLikeStoryDto {
    private Long id;
    private String title;
    private String description;
    private String color;
    private LocalDateTime date;
    private Long userId;
    private String profileImgUrl;
    private String nickname;


    @Builder
    public MypageLikeStoryDto(Long id, String title, String description, String color, LocalDateTime date, Long userId, String profileImgUrl, String nickname) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.date = date;
        this.userId = userId;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}
