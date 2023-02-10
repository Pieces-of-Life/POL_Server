package com.umc.pol.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MypageGetResponseDto {
    private List<MypageLikeStoryDto> story;
    private List<MypageChatDto> chat;

    @Builder
    public MypageGetResponseDto(List<MypageLikeStoryDto> story, List<MypageChatDto> chat) {
        this.story = story;
        this.chat = chat;
    }
}
