package com.umc.pol.domain.chat.dto;

import com.umc.pol.domain.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;
import java.util.HashSet;
import java.util.Set;

/*

 */

@Getter
public class ChatRoom {
    private String roomId;
    private String name;
    // 채팅방은 입장한 클라들 정보 갖고 있어야 하므로 웹소켓 세션 정보 리스트 멤버로 갖는다.
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    // 입장, 대화 기능 있으므로 분기 처리.
    // 입장 시 채팅룸의 세션 정보에 클라의 세션 리스트 추가해뒀다가 채팅룸에 메시지 도착할 경우
    // 채팅룸 모든 세션에 메시지 발송하면 채팅 완성.
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}