package com.umc.pol.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MypageLikeStoryDto {
    private Long id;
    private String title;
    private String description;
    private String color;
    private boolean isPick;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    private Long likeNum;
    private String profileImgUrl;
    private String nickname;
}
