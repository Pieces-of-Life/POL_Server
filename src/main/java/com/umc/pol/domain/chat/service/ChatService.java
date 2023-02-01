package com.umc.pol.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.pol.domain.chat.dto.ChatPageDto;
import com.umc.pol.domain.chat.dto.ChatRoom;
import com.umc.pol.domain.chat.dto.GetChatSpecDto;
import com.umc.pol.domain.chat.entity.ChatContents;
import com.umc.pol.domain.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

// list를 dto로 반환 : https://velog.io/@nyong_i/List를-Dto로-반환하는-방법-RESTful-API

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ObjectMapper objectMapper;
    // 서버에 생성된 모든 채팅방 정보 모아둔 구조체
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>(); // 채팅룸 정보저장은 우선 외부저장소 말고 해시맵으로. 추후 수정
    }


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

    // 모든 채팅룸 조회
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    // id로 채팅룸 조회
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    /*// 채팅룸 생성
    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }*/

    // 메시지 전송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);

        }
    }
}