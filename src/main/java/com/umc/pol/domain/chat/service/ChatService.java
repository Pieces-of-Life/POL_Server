package com.umc.pol.domain.chat.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.Chat;
import com.umc.pol.domain.chat.dto.Chatroom;
import com.umc.pol.domain.chat.dto.ChatroomId;
import com.umc.pol.domain.chat.dto.Description;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final StoryRepository storyRepository;

    public String createChatroom(String chatroomId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String path = "chatroom" + chatroomId;
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(path).document(chat.getMessage()).set(chat);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public List<Object> getChatroom(String chatroomId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatroom" + chatroomId;
        CollectionReference documentReference = db.collection(path);
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        return document.toObjects(Object.class);
    }

    public List<Chat> getChats(String chatroomId) throws ExecutionException, InterruptedException {
        List<Chat> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatroom" + chatroomId;
        ApiFuture<QuerySnapshot> future = db.collection(path).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Chat.class));
        }
        return list;
    }

    // 모든 채팅방의 data 나옴
    public List<Chatroom> all() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference documentReference = dbFirestore.collection(path);
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        return document.toObjects(Chatroom.class);
    }

    // 모든 채팅방 이름 가져오기
    public List<Object> getAllChatroomName() throws ExecutionException, InterruptedException {
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        ApiFuture<QuerySnapshot> future = db.collection(path).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.getId());
        }
        return list;
    }

    // 특정 채팅방 데이터 가져오기
    public Object getChatroomDetail(String chatroomId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference =
                firestore.collection("chatrooms").document(chatroomId);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(Object.class);
        } else {
            return null;
        }
    }

    // 특정 채팅방 채팅 컬렉션 가져오기
    public Object getChatroomChats(String chatroomId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> documentReference = firestore.collection("chatrooms").document(chatroomId).collection("chats").get();

        QuerySnapshot document = documentReference.get();

        return document.toObjects(Object.class);
    }

    // 채팅방 만들기
    // https://cloud.google.com/firestore/docs/samples/firestore-data-set-from-map-nested?hl=ko
    public void makeroom(Long storyId, Long senderId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        String path = "chatrooms";
        String childPath = "chatroom" + storyId + "_" + senderId;

        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 스토리입니다."));

        String WriterId = story.getUser().getId().toString();

        String now = LocalDateTime.now().toString();

        // 방 설명 -> 한번만 하면 될텐데
        Description description = new Description();
        description.setDate(now);
        description.setId(childPath); // 아니면 uuid
        description.setSender(senderId);
        description.setWriter(WriterId);
        db.collection(path).document(childPath).set(description);


        System.out.println("!!!!" + now.toString());

        // 채팅 말풍선 하나 하나
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", now);
        docData.put("message", chat.getMessage());
        // docData.put("userId", chat.getUserId());
        docData.put("userId", senderId);

        ApiFuture<WriteResult> future = db.collection(path).document(childPath).collection("chats").document(chat.getMessage()).set(docData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }

    // 채팅 보내기
    public void sendChat(String storyId, Long senderId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        String path = "chatrooms";

        String now = LocalDateTime.now().toString();

        System.out.println("!!!!" + now.toString());

        // 채팅 말풍선 하나 하나
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", now);
        docData.put("message", chat.getMessage());
        docData.put("userId", senderId);

        ApiFuture<WriteResult> future = db.collection(path).document(storyId).collection("chats").document(chat.getMessage()).set(docData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }


    // 모든 채팅방 목록 가져오기 (내가 주고받은 채팅방 목록 가져오기)
    // 마지막 채팅 보낸 사람 닉네임, 프로필 사진, 메시지, 날짜
    public List<ChatroomId> getMyChats(Long userId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference documentReference = dbFirestore.collection(path);
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        return document.toObjects(ChatroomId.class);
    }

    // 모든 채팅방들의 ( 채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜 ) 리스트 get
    public List<Object> getMyChatCover(Long userId) throws ExecutionException, InterruptedException {

        // 모든 채팅방 이름
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        ApiFuture<QuerySnapshot> future = db.collection(path).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.getId());
        }

        // 모든 채팅방의 chats
        List<Object> list2 = new ArrayList<>();
        int i=0;
        for (Object obj : list) {
            // 날짜 정렬
            // ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date").get();
            // 가장 최근 메시지 하나만
            ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get();

            QuerySnapshot document = documentReference.get();

            Collection<Object> objects = document.toObjects(Object.class);

            // objects.add("roomId??");
            objects.add(list.get(i)); // 채팅방 id
            i++;
            list2.add(objects);
        }

        return list2;
    }
}