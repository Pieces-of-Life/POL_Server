package com.umc.pol.domain.chat.repository;

import com.umc.pol.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 챗룸 id로 조회
    ChatRoom findById(long id);
}