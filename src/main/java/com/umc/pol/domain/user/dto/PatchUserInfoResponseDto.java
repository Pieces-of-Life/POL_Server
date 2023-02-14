package com.umc.pol.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchUserInfoResponseDto {
    private String profileImgUrl;
    private String nickname;

    @Builder
    public PatchUserInfoResponseDto(String profileImgUrl, String nickname) {
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;

    }
}
