package com.umc.pol.domain.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.pol.domain.chat.dto.ChatMessage;
import com.umc.pol.domain.chat.dto.ChatRoom;
import com.umc.pol.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
    소켓 통신은 서버와 클라가 1:N 관계
    서버는 여러 클라가 발송한 메시지 받아 처리할 핸들러 필요
    클라로 받은 메시지 로그로 출력하고 클라에 환영 메시지 보냄

    웹소켓 클라로부터 메시지 전달받아 채팅 메시지로 변환
    전달받은 메시지에 담긴 채팅발 id로 방송 대상 채팅방 정보 조회
    방 입장중인 모든 클라에게 타입에 따른 메시지 발송
    https://www.daddyprogrammer.org/post/4077/spring-websocket-chatting/
*/

@Slf4j
@RequiredArgsConstructor
@Component
public class WebsocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        /*TextMessage textMessage = new TextMessage("welcome to chatting server!!!");
        session.sendMessage(textMessage);*/
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }
}