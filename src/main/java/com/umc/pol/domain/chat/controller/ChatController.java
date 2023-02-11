package com.umc.pol.domain.chat.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.Chat;
import com.umc.pol.domain.chat.dto.Chatroom;
import com.umc.pol.domain.chat.dto.ChatroomId;
import com.umc.pol.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

// https://firework-ham.tistory.com/111
// https://www.youtube.com/watch?v=clH7SxG-Vdc&list=PLS1QulWo1RIYMQcf1y2bqQZbpXLzpKDUL&index=1

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

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

    // 채팅방 만들기 (토큰 적용)
    // 방 생성 시 이름 : "chatroom" + "이야기id_" + "보내는사람id" = "chatroom1_2"
    @PostMapping("/makeroom")
    public void makeroom(@RequestParam Long storyId, HttpServletRequest request, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        Long senderId = (Long) request.getAttribute("id");
        chatService.makeroom(storyId, senderId, chat);
    }

    // 채팅 보내기 (토큰 적용)
    @PostMapping("/send")
    public void sendChat(@RequestParam String chatroomId, HttpServletRequest request, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        Long senderId = (Long) request.getAttribute("id");
        chatService.sendChat(chatroomId, senderId, chat);
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

    // 모든 채팅방 목록 가져오기 (내가 주고받은 채팅방 목록 가져오기)
    // 마지막 채팅 보낸 사람 닉네임, 프로필 사진, 메시지, 날짜
    @GetMapping("/mychats")
    public List<ChatroomId> getMyChats(HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("id");
        return chatService.getMyChats(userId);
    }

    // 모든 채팅방들의 ( 채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜 ) 리스트 get
    @GetMapping("/mychatcover")
    public List<Object> getMyChatCover(HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("id");
        return chatService.getMyChatCover(userId);
    }







    // https://cloud.google.com/firestore/docs/samples/firestore-query-filter-eq-string?hl=ko
    @GetMapping("/test")
    public void test() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        /*CollectionReference cities = db.collection("cities");
        Query query = cities.whereEqualTo("state", "CA");*/
        // 메시지들
        CollectionReference messages = db.collection("chatrooms").document("chatroom1_8").collection("chats");
        // 유저id가 8인애들
        Query query = messages.whereEqualTo("userId", 8);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            System.out.println("!!!!!!!"+document.getId());
        }
    }
}