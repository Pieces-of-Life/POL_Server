package com.umc.pol.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAccountDto {
    private Long id;
    private KakaoAccount kakaoAccount;

    public static KakaoAccountDto fail() {
        return null;
    }
}
