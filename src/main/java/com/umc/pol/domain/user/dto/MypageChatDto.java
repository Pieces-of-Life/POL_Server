package com.umc.pol.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class MypageChatDto {
    private Long id;
    private String nickname;
    private String profileImgUrl;
    private LocalDateTime date;
    private String content;

    @Builder
    public MypageChatDto(Long id, String nickname, String profileImgUrl, LocalDateTime date, String content) {
        this.id = id;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.date = date;
        this.content = content;
    }
}
