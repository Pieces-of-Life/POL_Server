package com.umc.pol.domain.chat.dto;

import lombok.*;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatCoverDto {

    private Collection<Object> data;
//    private String chatroomId;


    @Builder
    public ChatCoverDto(Collection<Object> data) {
        this.data = data;
//        this.chatroomId = chatroomId;
    }
}