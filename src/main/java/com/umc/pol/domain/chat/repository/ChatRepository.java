package com.umc.pol.domain.chat.repository;

import com.umc.pol.domain.chat.entity.ChatContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatContents, Long> {
    // 쪽지 상세 조회
    List<ChatContents> findByChatRoomId(long chatRoomId);
}
