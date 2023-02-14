package com.umc.pol.domain.chat.dto;

import com.umc.pol.domain.story.dto.StoryCoverDto;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoverChatDto {
    private String chatroomId;
    private Long userId;
    private String nickname;
    private String profileImg;

    @Builder
    public CoverChatDto(String chatroomId, Long userId, String nickname, String profileImg) {
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}