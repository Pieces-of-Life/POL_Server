package com.umc.pol.domain.chat.controller;

import com.umc.pol.domain.chat.dto.Chat;
import com.umc.pol.domain.chat.dto.Chatroom;
import com.umc.pol.domain.chat.dto.ChatroomId;
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

    @GetMapping("")
    public List<Chat> getChatroom(@RequestParam String chatroomId) throws InterruptedException, ExecutionException {
//        return chatService.getChatroom(chatroomId);
        return chatService.getChats(chatroomId);
    }

    //////////

    // 모든 채팅방의 data 나옴
    @GetMapping("/all")
    public List<Chatroom> all() throws InterruptedException, ExecutionException {
        return chatService.all();
    }

    // 모든 채팅방 이름 가져오기
    @GetMapping("/lists")
    public List<Object> getAllChatroomName() throws InterruptedException, ExecutionException {
        return chatService.getAllChatroomName();
    }

    // 특정 채팅방 데이터 가져오기
    @GetMapping("/{chatroomId}")
    public Object getChatroomDetail(@PathVariable("chatroomId") String chatroomId) throws Exception {
        return chatService.getChatroomDetail(chatroomId);
    }

    // 특정 채팅방 채팅 컬렉션 가져오기
    // http://localhost:8080/chat/chatroom1_1/chats
    @GetMapping("/{chatroomId}/chats")
    public Object getChatroomChats(@PathVariable("chatroomId") String chatroomId) throws Exception {
        return chatService.getChatroomChats(chatroomId);
    }

    // 채팅 보내기
    @PostMapping("/send")
    public void sendChat(@RequestParam Long storyId, @RequestParam String senderId, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        chatService.sendChat(storyId, senderId, chat);
    }

    // 내가 주고받은 채팅방 이름 가져오기
    @GetMapping("/mychats")
    public List<ChatroomId> getMyChats() throws Exception {
        return chatService.getMyChats();
    }
}