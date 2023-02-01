package com.umc.pol.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/*
    pub/sub 방식 사용하면 구독자 관리 알아서.
    웹소켓 세션 관리 필요 x. 발송 구현 알아서, 필요 x
 */

// redis로 저장되는 객체들은 serializable 해야 함
@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}