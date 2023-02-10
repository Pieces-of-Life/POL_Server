package com.umc.pol.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoJwtResponseDto {
    private String accessToken;

    @Builder
    public KakaoJwtResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
