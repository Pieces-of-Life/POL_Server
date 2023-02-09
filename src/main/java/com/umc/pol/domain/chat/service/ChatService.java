package com.umc.pol.domain.chat.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<Object> getChats(String chatroomId) throws ExecutionException, InterruptedException {
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatroom" + chatroomId;
        ApiFuture<QuerySnapshot> future = db.collection(path).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Object.class));
        }
        return list;
    }

    public List<Object> xx() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference documentReference = dbFirestore.collection(path);
        ApiFuture<QuerySnapshot> future = documentReference.get();
        QuerySnapshot document = future.get();

        return document.toObjects(Object.class);
    }
}