package com.umc.pol.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoTokenResponseDto {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshTokenExpiresIn;

    public static KakaoTokenResponseDto fail() {
        return new KakaoTokenResponseDto(null, null);
    }

    private KakaoTokenResponseDto(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
