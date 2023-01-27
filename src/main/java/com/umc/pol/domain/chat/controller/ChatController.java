package com.umc.pol.domain.chat.controller;


import com.umc.pol.domain.chat.dto.GetChatSpecDto;
import com.umc.pol.domain.chat.entity.ChatRoom;
import com.umc.pol.domain.chat.service.ChatService;
import com.umc.pol.global.response.ListResponse;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ResponseService responseService;

    // old -> 데이터만 반환
    // 쪽지 상세 조회
    @GetMapping("/spec/{chatRoomId}/old")
    public List<GetChatSpecDto> getChatSpecOld(@PathVariable("chatRoomId") long chatRoomId) {
        // 엔티티 반환 x, dto 반환이 좋다.
        return chatService.getChatSpec(chatRoomId);
    }

    // 양식 맞춰서 반환
    // 쪽지 상세 조회
    @GetMapping("/spec/{chatRoomId}")
    public ListResponse<GetChatSpecDto> getChatSpec(@PathVariable("chatRoomId") long chatRoomId) {
        // 엔티티 반환 x, dto 반환이 좋다.
        return responseService.getListResponse(chatService.getChatSpec(chatRoomId));
    }

    ///////////////
    // 테스트
    // old -> 데이터만 반환
    // 챗룸 조회
    @GetMapping("/{chatRoomId}/old")
    public ChatRoom testOld(@PathVariable("chatRoomId") long chatRoomId) {
        return chatService.findById(chatRoomId);
    }

    // 양식 맞춰서 반환
    // 챗룸 id로 조회
    @GetMapping("/{chatRoomId}")
    public SingleResponse<ChatRoom> test(@PathVariable("chatRoomId") long chatRoomId) {
        return responseService.getSingleResponse(chatService.findById(chatRoomId));
    }
}