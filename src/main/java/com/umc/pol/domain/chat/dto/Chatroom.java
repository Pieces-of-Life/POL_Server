package com.umc.pol.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Chatroom {
    private String id;
    private String sender;
    private String writer;
    private String date;
}