package com.umc.pol.domain.chat.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
// @AllArgsConstructor // https://velog.io/@mooh2jj/올바른-엔티티-Builder-사용법
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long chatRoomId;

    @Column
    private long userId;

    @Column
    private String content;

    @Column
    private LocalDateTime datetime;

    @Builder
    public ChatContents(long chatRoomId, long userId, String content) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.content = content;
    }
}