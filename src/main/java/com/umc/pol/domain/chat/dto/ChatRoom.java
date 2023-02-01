package com.umc.pol.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/*
    pub/sub 방식 사용하면 구독자 관리 알아서.
    웹소켓 세션 관리 필요 x. 발송 구현 알아서, 필요 x
 */

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}