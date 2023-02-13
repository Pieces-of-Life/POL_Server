package com.umc.pol.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchUserInfoRequestDto {
    private String nickname;

    @Builder
    public PatchUserInfoRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
