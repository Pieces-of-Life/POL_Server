package com.umc.pol.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class GetChatSpecDto {

    private long chatRoomId;

    private long userId;

    private String content;

    private LocalDateTime datetime;

    @Builder
    public GetChatSpecDto(long chatRoomId, long userId, String content, LocalDateTime datetime) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.content = content;
        this.datetime = datetime;
    }
}