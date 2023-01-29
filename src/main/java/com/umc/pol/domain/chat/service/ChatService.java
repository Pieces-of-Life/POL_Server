package com.umc.pol.domain.chat.service;

import com.umc.pol.domain.chat.dto.ChatPageDto;
import com.umc.pol.domain.chat.dto.GetChatSpecDto;
import com.umc.pol.domain.chat.entity.ChatContents;
import com.umc.pol.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    // list를 dto로 반환 : https://velog.io/@nyong_i/List를-Dto로-반환하는-방법-RESTful-API

    // 쪽지 상세 페이지 (story 표지 + chatList)
    public ChatPageDto getChatSpecPage(long id) {

        List<ChatContents> chatList = chatRepository.findByChatRoomId(id);
        List<GetChatSpecDto> chatListDto = new ArrayList<>();

        for (ChatContents content : chatList) {
            GetChatSpecDto dto = GetChatSpecDto.builder()
                    .userId(content.getUserId())
                    .content(content.getContent())
                    .datetime(content.getDatetime())
                    .build();

            chatListDto.add(dto);
        }

        ChatPageDto chatPage = ChatPageDto.builder()
                .chatList(chatListDto)
                .story("스토리 표지 api 개발 완료 시 추가")
                .build();

        return chatPage;
    }
}