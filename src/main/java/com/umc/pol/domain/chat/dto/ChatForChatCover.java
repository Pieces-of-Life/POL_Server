package com.umc.pol.domain.chat.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatForChatCover {
    private String date;
    private String message;
    private Long userId;

    @Builder
    public ChatForChatCover(String date, String message, Long userId) {
        this.date = date;
        this.message = message;
        this.userId = userId;
    }
}