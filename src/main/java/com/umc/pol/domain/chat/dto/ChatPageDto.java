package com.umc.pol.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatPageDto {

    private String story;

    private List<GetChatSpecDto> chatList;

    @Builder
    public ChatPageDto(String story, List<GetChatSpecDto> chatList) {
        this.story = story;
        this.chatList = chatList;
    }
}