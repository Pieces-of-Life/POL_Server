package com.umc.pol.domain.chat.controller;

import com.umc.pol.domain.chat.dto.Chat;
import com.umc.pol.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

// https://firework-ham.tistory.com/111
// https://www.youtube.com/watch?v=clH7SxG-Vdc&list=PLS1QulWo1RIYMQcf1y2bqQZbpXLzpKDUL&index=1

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    // 방 생성 시 이름? : "chatroom" + "이야기id_" + "글쓴이id" + "보내는사람id" = "chatroom1_1_2"
    // 채팅 시작
    @PostMapping("")
    public String createChatroom(@RequestParam String chatroomId, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        return chatService.createChatroom(chatroomId, chat);
    }

    // 채팅방 id로
    @GetMapping("")
    public List<Object> getChatroom(@RequestParam String chatroomId) throws InterruptedException, ExecutionException {
//        return chatService.getChatroom(chatroomId);
        return chatService.getChats(chatroomId);
    }

    @GetMapping("/xx")
    public List<Object> xx() throws InterruptedException, ExecutionException {
//        return chatService.getChatroom(chatroomId);
        return chatService.xx();
    }
}