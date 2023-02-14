package com.umc.pol.domain.chat.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.umc.pol.domain.chat.dto.*;
import com.umc.pol.domain.story.dto.StoryCoverDto;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;

    /*public String createChatroom(String chatroomId, Chat chat) throws ExecutionException, InterruptedException {
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
    }*/

    ////////

    // 1. 채팅방 만들기
    // 방 생성 시 이름 : "chatroom" + "이야기id_" + "보내는사람id" = "chatroom1_2"
    // https://cloud.google.com/firestore/docs/samples/firestore-data-set-from-map-nested?hl=ko
    public void makeroom(Long storyId, Long senderId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        String path = "chatrooms";
        String childPath = "chatroom" + storyId + "_" + senderId;

        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 스토리입니다."));

        Long WriterId = story.getUser().getId();

        String now = LocalDateTime.now().toString();

        Description description = new Description();
        description.setDate(now);
        description.setId(childPath);
        description.setSender(senderId);
        description.setWriter(WriterId);
        db.collection(path).document(childPath).set(description);

        // 채팅 말풍선 하나 하나
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", now);
        docData.put("message", chat.getMessage());
        // docData.put("userId", chat.getUserId());
        docData.put("userId", senderId);

        ApiFuture<WriteResult> future = db.collection(path).document(childPath).collection("chats").document(chat.getMessage()).set(docData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }

    // 2. 채팅 보내기
    public void sendChat(String storyId, Long senderId, Chat chat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        String path = "chatrooms";

        String now = LocalDateTime.now().toString();

        // 채팅 말풍선 하나 하나
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", now);
        docData.put("message", chat.getMessage());
        docData.put("userId", senderId);

        ApiFuture<WriteResult> future = db.collection(path).document(storyId).collection("chats").document(chat.getMessage()).set(docData);
        System.out.println("Update time : " + future.get().getUpdateTime());
    }

    /*// 3. 특정 채팅방 채팅 컬렉션 가져오기
    public Object getChatroomChats(String chatroomId) throws Exception {

        // 이야기 id
        Long storyId = Long.valueOf(Integer.parseInt(chatroomId.substring(8, chatroomId.indexOf("_"))));

        // 이야기 표지
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreatedAt())
                .color(story.getColor())
                .likeCnt(story.getLikeCnt())
                .profileImgUrl(story.getUser().getProfileImg())
                .nickname(story.getUser().getNickname())
                .build();


        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> documentReference = firestore.collection("chatrooms").document(chatroomId).collection("chats").orderBy("date").get();

        QuerySnapshot document = documentReference.get();

        Collection<Object> objects = document.toObjects(Object.class);

        objects.add(storyCover);

        return objects;
    }*/

    // 3. 특정 채팅방 채팅 컬렉션 가져오기
    public ChatRoomPageDto getChatroomChats(String chatroomId) throws Exception {

        // 이야기 id
        Long storyId = Long.valueOf(Integer.parseInt(chatroomId.substring(8, chatroomId.indexOf("_"))));

        // 이야기 표지
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreatedAt())
                .color(story.getColor())
                .likeCnt(story.getLikeCnt())
                .profileImgUrl(story.getUser().getProfileImg())
                .nickname(story.getUser().getNickname())
                .build();

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> documentReference = firestore.collection("chatrooms").document(chatroomId).collection("chats").orderBy("date", Query.Direction.DESCENDING).get();

        QuerySnapshot document = documentReference.get();

        Collection<Object> objects = document.toObjects(Object.class);

        ChatRoomPageDto chatRoomPage = ChatRoomPageDto.builder()
                .story(storyCover)
                .chats(objects)
                .build();

        return chatRoomPage;
    }

    /*// 모든 채팅방 이름 // 이거
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        ApiFuture<QuerySnapshot> future = db.collection(path).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.getId());
        }*/

    /*// 4. '나의' 모든 채팅방들의 표지 ( 채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜 ) 리스트 get
    // 모든에서 내가 주고받은 으로 바꿔야 함.
    // --> writer가 나거나, sender가 나거나.
    // chatrooms.where(writer || sender == 내id)
    public List<Object> getMyChatCover(Long userId) throws ExecutionException, InterruptedException {

        // 여기

        // '나의' 모든 채팅방 이름
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference cities = db.collection(path);
        // sender가 나인 경우
        Query query = cities.whereEqualTo("sender", userId);
        List<QueryDocumentSnapshot> documents1 = query.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents1) {
            list.add(document.getId());
        }

        // writer가 나인 경우
        Query query2 = cities.whereEqualTo("writer", userId);
        List<QueryDocumentSnapshot> documents2 = query2.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents2) {
            list.add(document.getId());
        }

        // 모든 채팅방의 chats
        List<Object> list2 = new ArrayList<>();
        int i = 0;
        for (Object obj : list) {
            // obj가 방 이름
            // 날짜 정렬
            // ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date").get();
            // 가장 최근 메시지 하나만
            ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get();

            QuerySnapshot document = documentReference.get();

            // Collection<Object> objects = document.toObjects(Object.class);
            // Collection<ChatForChatCover> objects = document.toObjects(ChatForChatCover.class);
            Collection<Object> objects = document.toObjects(Object.class);

            objects.add(list.get(i)); // 채팅방 id (이름)

            String chatroomName = list.get(i).toString();
            System.out.println("!!!! chatroomName = " + chatroomName);

            String storyId = chatroomName.substring(8, chatroomName.indexOf("_"));
            System.out.println("!!!! storyId = " + storyId);

            i++;
            list2.add(objects); // 이건 dto로 만들자?
        }

        return list2;
    }*/

    /*// 4. '나의' 모든 채팅방들의 표지 ( 채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜 ) 리스트 get
    // 모든에서 내가 주고받은 으로 바꿔야 함.
    // --> writer가 나거나, sender가 나거나.
    // chatrooms.where(writer || sender == 내id)
    public ChatCoverDto getMyChatCover(Long userId) throws ExecutionException, InterruptedException {

        // 여기

        // '나의' 모든 채팅방 이름
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference cities = db.collection(path);
        // sender가 나인 경우
        Query query = cities.whereEqualTo("sender", userId);
        Collection<QueryDocumentSnapshot> documents1 = query.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents1) {
            list.add(document.getId());
        }

        // writer가 나인 경우
        Query query2 = cities.whereEqualTo("writer", userId);
        Collection<QueryDocumentSnapshot> documents2 = query2.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents2) {
            list.add(document.getId());
        }

        // 모든 채팅방의 chats
        Collection<Object> list2 = new ArrayList<>();
        int i = 0;
        for (Object obj : list) {
            // obj가 방 이름
            // 날짜 정렬
            // ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date").get();
            // 가장 최근 메시지 하나만
            ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get();

            QuerySnapshot document = documentReference.get();

            // Collection<Object> objects = document.toObjects(Object.class);
            // Collection<ChatForChatCover> objects = document.toObjects(ChatForChatCover.class);
            Collection<Object> objects = document.toObjects(Object.class);

            objects.add(list.get(i)); // 채팅방 id (이름)

            String chatroomName = list.get(i).toString();
            System.out.println("!!!! chatroomName = " + chatroomName);

            String storyId = chatroomName.substring(8, chatroomName.indexOf("_"));
            System.out.println("!!!! storyId = " + storyId);


            String senderId = chatroomName.substring(chatroomName.indexOf("_") + 1);
            System.out.println("!$!$! senderId = " + senderId);

            Long senderIdLong = Long.parseLong(senderId);

            Long lastMessageWriterId;

            if (userId == senderIdLong) {
                lastMessageWriterId = userId;
            } else {
                lastMessageWriterId = senderIdLong;
            }

            objects.add(lastMessageWriterId); // 마지막 메시지 작성자 id
            User user = userRepository.findById(lastMessageWriterId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));
            objects.add(user.getNickname()); // 마지막 메시지 작성자 닉네임
            objects.add(user.getProfileImg()); // 마지막 메시지 작성자 프로필 사진

            i++;
            list2.add(objects); // 이건 dto로 만들자?
        }


        ChatCoverDto chatCover = ChatCoverDto.builder()
                .data(list2)
                .build();

        return chatCover;
    }*/

    // 4. '나의' 모든 채팅방들의 표지 ( 채팅방id, 마지막 채팅 메시지, 마지막 채팅 작성자, 날짜 ) 리스트 get
    // 모든에서 내가 주고받은 으로 바꿔야 함.
    // --> writer가 나거나, sender가 나거나.
    // chatrooms.where(writer || sender == 내id)
    public ChatCoverDto getMyChatCover(Long userId) throws ExecutionException, InterruptedException {

        // '나의' 모든 채팅방 이름
        List<Object> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        String path = "chatrooms";
        CollectionReference cities = db.collection(path);
        // sender가 나인 경우
        Query query = cities.whereEqualTo("sender", userId);
        Collection<QueryDocumentSnapshot> documents1 = query.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents1) {
            list.add(document.getId());
        }

        // writer가 나인 경우
        Query query2 = cities.whereEqualTo("writer", userId);
        Collection<QueryDocumentSnapshot> documents2 = query2.get().get().getDocuments();

        for (QueryDocumentSnapshot document : documents2) {
            list.add(document.getId());
        }

        // 모든 채팅방의 chats
        Collection<Object> list2 = new ArrayList<>();
        int i = 0;
        for (Object obj : list) {
            // obj가 방 이름
            // 날짜 정렬
            // ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date").get();
            // 가장 최근 메시지 하나만
            ApiFuture<QuerySnapshot> documentReference = db.collection("chatrooms").document(obj.toString()).collection("chats").orderBy("date", Query.Direction.DESCENDING).limit(1).get();

            QuerySnapshot document = documentReference.get();

            // Collection<Object> objects = document.toObjects(Object.class);
            // Collection<ChatForChatCover> objects = document.toObjects(ChatForChatCover.class);
            Collection<Object> objects = document.toObjects(Object.class);

            /*CoverChatDto coverChat = CoverChatDto.builder()
                    .chatroomId(null)
                    .userId(null)
                    .nickname(null)
                    .profileImg(null)
                    .build();

            objects.add(coverChat);*/

//            objects.add(list.get(i)); // 1. 채팅방 id (이름)

            String chatroomName = list.get(i).toString();

            String senderId = chatroomName.substring(chatroomName.indexOf("_") + 1);

            Long senderIdLong = Long.parseLong(senderId);

            Long lastMessageWriterId;

            if (userId == senderIdLong) {
                lastMessageWriterId = userId;
            } else {
                lastMessageWriterId = senderIdLong;
            }

//            objects.add(lastMessageWriterId); // 2. 마지막 메시지 작성자 id
            User user = userRepository.findById(lastMessageWriterId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));
//            objects.add(user.getNickname()); // 3. 마지막 메시지 작성자 닉네임
//            objects.add(user.getProfileImg()); // 4. 마지막 메시지 작성자 프로필 사진

            CoverChatDto coverChat = CoverChatDto.builder() // 4개 dto로 만들고 한번에 넣기
                    .chatroomId(list.get(i).toString()) // 1
                    .userId(lastMessageWriterId) // 2
                    .nickname(user.getNickname()) // 3
                    .profileImg(user.getProfileImg()) // 4
                    .build();

            objects.add(coverChat); // dto 만든거 objects에 넣기

            i++;

            list2.add(objects); // 이건 dto로 만들자?
        }


        ChatCoverDto chatCover = ChatCoverDto.builder()
                .data(list2)
                .build();

        return chatCover;
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
}