package com.umc.pol.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
public class UserInfoGetResponseDto {
    private String profileImgUrl;
    private String nickname;
    private Long score;
    private Long level;

    @Builder
    public UserInfoGetResponseDto(String profileImgUrl, String nickname, Long score, Long level) {
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.score = score;
        this.level = level;
    }
}
