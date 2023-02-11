package com.umc.pol.domain.chat.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.*;
import com.umc.pol.domain.chat.service.ChatService;
import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

// https://firework-ham.tistory.com/111
// https://www.youtube.com/watch?v=clH7SxG-Vdc&list=PLS1QulWo1RIYMQcf1y2bqQZbpXLzpKDUL&index=1

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final ResponseService responseService;

    /*// 채팅 시작
    @PostMapping("")
    public String createChatroom(@RequestParam String chatroomId, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        return chatService.createChatroom(chatroomId, chat);
    }

    @GetMapping("")
    public List<Chat> getChatroom(@RequestParam String chatroomId) throws InterruptedException, ExecutionException {
//        return chatService.getChatroom(chatroomId);
        return chatService.getChats(chatroomId);
    }*/

    //////////

    // 1. 채팅방 만들기 (토큰 적용)
    // 방 생성 시 이름 : "chatroom" + "이야기id_" + "보내는사람id" = "chatroom1_2"
    @Operation(summary = "채팅방 만들기", description = "방 생성 시 이름 : \"chatroom\" + \"이야기id_\" + \"보내는사람id\" = \"chatroom1_2\"")
    @PostMapping("/makeroom")
    public ChatSuccessResponse makeroom(@RequestParam Long storyId, HttpServletRequest request, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        Long senderId = (Long) request.getAttribute("id");
        chatService.makeroom(storyId, senderId, chat);

        ChatSuccessResponse response = ChatSuccessResponse.builder()
                .status(200L)
                .success(true)
                .message("SUCCESS")
                .build();

        return response;
    }

    // 2. 채팅 보내기 (토큰 적용)
    @Operation(summary = "채팅 보내기", description = "chatroomId 예시 : chatroom1_6, RequestBody에 message 넣어야 함.")
    @PostMapping("/send")
    public ChatSuccessResponse sendChat(@RequestParam String chatroomId, HttpServletRequest request, @RequestBody Chat chat) throws InterruptedException, ExecutionException {
        Long senderId = (Long) request.getAttribute("id");
        chatService.sendChat(chatroomId, senderId, chat);

        ChatSuccessResponse response = ChatSuccessResponse.builder()
                .status(200L)
                .success(true)
                .message("SUCCESS")
                .build();

        return response;
    }

    // 3. 특정 채팅방 채팅 컬렉션 가져오기
    // http://localhost:8080/chat/chatroom1_1/chats
    @Operation(summary = "특정 채팅방 채팅 목록 가져오기", description = "chatroomId 예시 : chatroom1_6")
    @GetMapping("/{chatroomId}/chats")
    public SingleResponse<ChatRoomPageDto> getChatroomChats(@PathVariable("chatroomId") String chatroomId) throws Exception {
        return responseService.getSingleResponse(chatService.getChatroomChats(chatroomId));
    }

    /*// 4-0. 모든 채팅방들의 표지
    // ( 채팅방id, 마지막 채팅 메시지, 날짜, 마지막 채팅 작성자 id, 작성자 닉네임, 작성자 프로필 이미지) 리스트 get
    @Operation(summary = "채팅방 표지 리스트", description = "채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜, 마지막 채팅 친 유저 닉네임, 유저 플필")
    @GetMapping("/mychatcover")
    public List<Object> getMyChatCover(HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("id");
        return chatService.getMyChatCover(userId);
    }*/

    // 4. '나의' 모든 채팅방들의 표지
    // ( 채팅방id, 마지막 채팅 메시지, 날짜, 마지막 채팅 작성자 id, 작성자 닉네임, 작성자 프로필 이미지) 리스트 get
    @Operation(summary = "채팅방 표지 리스트", description = "채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜, 마지막 채팅 친 유저 닉네임, 유저 플필")
    @GetMapping("/mychatcover")
    public ChatCoverDto getMyChatCover(HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("id");
        return chatService.getMyChatCover(userId);
    }

    // 특정 채팅방 데이터 가져오기
    @GetMapping("/{chatroomId}")
    @Operation(hidden = true)
    public Object getChatroomDetail(@PathVariable("chatroomId") String chatroomId) throws Exception {
        return chatService.getChatroomDetail(chatroomId);
    }

    // 모든 채팅방의 data 나옴
    @Operation(hidden = true)
    @GetMapping("/all")
    public List<Chatroom> all() throws InterruptedException, ExecutionException {
        return chatService.all();
    }

    // 모든 채팅방 이름 가져오기
    @Operation(hidden = true)
    @GetMapping("/lists")
    public List<Object> getAllChatroomName() throws InterruptedException, ExecutionException {
        return chatService.getAllChatroomName();
    }

    // 모든 채팅방 목록 가져오기 (내가 주고받은 채팅방 목록 가져오기)
    // 마지막 채팅 보낸 사람 닉네임, 프로필 사진, 메시지, 날짜
    @Operation(hidden = true)
    @GetMapping("/mychats")
    public List<ChatroomId> getMyChats(HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("id");
        return chatService.getMyChats(userId);
    }









    // https://cloud.google.com/firestore/docs/samples/firestore-query-filter-eq-string?hl=ko
    @Operation(hidden = true)
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