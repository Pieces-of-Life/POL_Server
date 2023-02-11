package com.umc.pol.domain.chat.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatCover {
    private String chatroomId;
    private String userNickname;
    private String userProfileImg;
    private ChatForChatCover chat;

    @Builder
    public ChatCover(String chatroomId, String userNickname, String userProfileImg, ChatForChatCover chat) {
        this.chatroomId = chatroomId;
        this.userNickname = userNickname;
        this.userProfileImg = userProfileImg;
        this.chat = chat;
    }
}