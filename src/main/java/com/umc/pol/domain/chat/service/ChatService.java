package com.umc.pol.domain.chat.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.Chat;
import com.umc.pol.domain.chat.dto.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatService {
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
    public List<Object> all() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference documentReference = dbFirestore.collection(path);
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        return document.toObjects(Object.class);
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
        if(documentSnapshot.exists()){
            return documentSnapshot.toObject(Object.class);
        }
        else{
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

    // 채팅 보내기
    // https://cloud.google.com/firestore/docs/samples/firestore-data-set-from-map-nested?hl=ko
    public void sendChat(String chatroomId, String senderId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        String path = "chatrooms";
        String childPath = "chatroom" + chatroomId + "_" + senderId;

        // 방 설명 -> 한번만 하면 될텐데
        Description description = new Description();
        description.setDate(null);
        description.setSender(senderId);
        description.setWriter("99");
        db.collection(path).document(childPath).set(description);


        // 채팅 말풍선 하나 하나
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", "2022.01.01");
        docData.put("message", chat.getMessage());
        docData.put("userId", chat.getUserId());

        ApiFuture<WriteResult> future = db.collection(path).document(childPath).collection("chats").document(chat.getMessage()).set(docData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }
}