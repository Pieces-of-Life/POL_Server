package com.umc.pol.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
    핸들러 이용해 웹소켓 활성화 하기 위한 config 파일 작성
 */

@RequiredArgsConstructor
@Configuration
@EnableWebSocket // 소켓 활성화
public class WebsocketConfig implements WebSocketConfigurer {
    private final WebsocketHandler websocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
