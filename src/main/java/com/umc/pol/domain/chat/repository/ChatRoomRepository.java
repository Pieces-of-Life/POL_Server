package com.umc.pol.domain.chat.repository;

import com.umc.pol.domain.chat.dto.ChatRoom;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.*;


@Repository
public class ChatRoomRepository {
    // 우선 정보 Map으로 관리
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms); // 최신순 반환
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}