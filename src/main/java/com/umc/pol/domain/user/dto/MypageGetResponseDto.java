package com.umc.pol.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MypageGetResponseDto {
    private List<MypageLikeStoryDto> mypageLikeStoryDto;
    private List<MypageChatDto> mypageChatDto;
}
