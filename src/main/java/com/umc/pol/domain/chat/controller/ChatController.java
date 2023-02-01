package com.umc.pol.domain.chat.controller;

import com.umc.pol.domain.chat.dto.ChatPageDto;
import com.umc.pol.domain.chat.dto.ChatRoom;
import com.umc.pol.domain.chat.service.ChatService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ResponseService responseService;

    // 쪽지 상세 페이지 (story 표지 + chatList)
    @GetMapping("/spec/{chatRoomId}")
    public SingleResponse<ChatPageDto> getChatSpecPage(@PathVariable("chatRoomId") long chatRoomId) {
        return responseService.getSingleResponse(chatService.getChatSpecPage(chatRoomId));
    }

    // 채팅룸 생성
    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    // 모든 채팅룸 조회
    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}