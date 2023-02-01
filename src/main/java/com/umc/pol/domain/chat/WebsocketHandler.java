package com.umc.pol.domain.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
    소켓 통신은 서버와 클라가 1:N 관계
    서버는 여러 클라가 발송한 메시지 받아 처리할 핸들러 필요
    클라로 받은 메시지 로그로 출력하고 클라에 환영 메시지 보냄
    https://www.daddyprogrammer.org/post/4077/spring-websocket-chatting/
*/

@Slf4j
@Component
public class WebsocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        TextMessage textMessage = new TextMessage("welcome to chatting server!!!");
        session.sendMessage(textMessage);
    }
}
