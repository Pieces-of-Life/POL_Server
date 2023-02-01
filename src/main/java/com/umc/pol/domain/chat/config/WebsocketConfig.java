package com.umc.pol.domain.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/*
    stomp 사용 위해 configureMessageBroker 구현
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // pub/sub 메시징 구현하기 위해 메시지 발행 요청 prefix는 /pub로 시작, 구독 요청은 /sub로 시작
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 웹소켓의 연결 엔드포인트
        // 따라서 개발 서버 접속 주소는 ws://localhost:8080/ws-stomp
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
    }
}
