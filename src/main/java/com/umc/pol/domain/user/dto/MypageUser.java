package com.umc.pol.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MypageUser {
    private String profileImgUrl;
    private String nickname;

    @Builder
    public MypageUser(String profileImgUrl, String nickname) {
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}
