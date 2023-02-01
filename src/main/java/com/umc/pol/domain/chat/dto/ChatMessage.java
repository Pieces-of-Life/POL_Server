package com.umc.pol.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

// 메시지 주고받기 위한 dto

@Getter
@Setter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
