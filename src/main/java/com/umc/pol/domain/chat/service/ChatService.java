package com.umc.pol.domain.chat.service;

import com.umc.pol.domain.chat.dto.GetChatSpecDto;
import com.umc.pol.domain.chat.entity.ChatContents;
import com.umc.pol.domain.chat.entity.ChatRoom;
import com.umc.pol.domain.chat.repository.ChatRepository;
import com.umc.pol.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    // list를 dto로 반환 : https://velog.io/@nyong_i/List를-Dto로-반환하는-방법-RESTful-API
    // 쪽지 상세 조회
    public List<GetChatSpecDto> getChatSpec(long chatRoomId) {
        List<ChatContents> chatList = chatRepository.findByChatRoomId(chatRoomId);
        List<GetChatSpecDto> chatListDto = new ArrayList<>();

        for (ChatContents content : chatList) {
            GetChatSpecDto dto = GetChatSpecDto.builder()
                    .chatRoomId(content.getChatRoomId())
                    .userId(content.getUserId())
                    .content(content.getContent())
                    .datetime(content.getDatetime())
                    .build();

            chatListDto.add(dto);
        }

        return chatListDto;
    }

    // 테스트
    // 챗룸 id로 조회
    public ChatRoom findById(long id) {
        return chatRoomRepository.findById(id);
    }
}