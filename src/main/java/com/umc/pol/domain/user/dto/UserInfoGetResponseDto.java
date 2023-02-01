package com.umc.pol.domain.user.dto;

import lombok.Data;

@Data
public class UserInfoGetResponseDto {
    private String profileImgUrl;
    private String nickname;
    private Long score;
    private Long level;
}
