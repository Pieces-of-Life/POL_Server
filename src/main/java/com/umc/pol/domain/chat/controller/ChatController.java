package com.umc.pol.domain.chat.controller;

import com.umc.pol.domain.chat.dto.ChatMessage;
import com.umc.pol.domain.chat.dto.ChatPageDto;
import com.umc.pol.domain.chat.service.ChatService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ResponseService responseService;
    private final SimpMessageSendingOperations messagingTemplate;

    /*// 쪽지 상세 페이지 (story 표지 + chatList)
    @GetMapping("/spec/{chatRoomId}")
    public SingleResponse<ChatPageDto> getChatSpecPage(@PathVariable("chatRoomId") long chatRoomId) {
        return responseService.getSingleResponse(chatService.getChatSpecPage(chatRoomId));
    }*/


    /*
        @MessageMapping 통해 웹소켓으로 들어오는 메시지 발행 처리
        클라는 prefix 붙여서 /pub/chat/message 바행 요청하면 컨트롤러에서 해당 메시지 받아 처리.
        메시지 발행되면 /sub/chat/room/{roomId}로 메시지를 send 하고
        클라에서는 해당 주소 구독(sub) 하고 있다가 메시지 전달되면 화면 출력
        /sub/chat/room/{roomId}는 채팅룹 구분 값. 즉 pub/sub에서 topic 역할
   */

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}