package com.umc.pol.domain.chat.dto;

import com.umc.pol.domain.story.dto.StoryCoverDto;
import lombok.*;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatRoomPageDto {
    private StoryCoverDto story;
    private Collection<Object> chats;

    @Builder
    public ChatRoomPageDto(StoryCoverDto story, Collection<Object> chats) {
        this.story = story;
        this.chats = chats;
    }
}