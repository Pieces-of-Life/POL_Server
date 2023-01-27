package com.umc.pol.domain.chat.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long storyId;

    @Column
    private long receiverId;

    @Column
    private long senderId;

    @Builder
    public ChatRoom(long storyId, long receiverId, long senderId) {
        this.storyId = storyId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }
}