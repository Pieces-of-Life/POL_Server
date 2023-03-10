package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.convert.QnaConverter;
import com.umc.pol.domain.story.convert.StoryTagConverter;
import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.dto.request.PostStoryRequest;
import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.response.PostStoryResponse;
import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.entity.StoryTag;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;

import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;


import com.umc.pol.domain.story.dto.request.QnaDto;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final StoryTagRepository storyTagRepository;
    private final QnaRepository qnaRepository;

    private final StoryTagConverter storyTagConverter;
    private final QnaConverter qnaConverter;
    private final LikeRepository likeRepository;


    public List<GetStoryResponse> getStoryList(Pageable pageable, Long cursorId) {
        List<GetStoryResponse> storyList = storyRepository.findStory(pageable, cursorId)
            .stream().map(GetStoryResponse::new)
            .collect(Collectors.toList());

        return storyList;
    }

    public List<GetStoryResponse> getUserMainStoryList(Pageable pageable, Long cursorId, Long userId) {
        List<GetStoryResponse> storyList = storyRepository.findUserMainStory(pageable, cursorId, userId)
            .stream().map(GetStoryResponse::new)
            .collect(Collectors.toList());

        return storyList;
    }

    @Transactional
    public PostStoryResponse postStory(PostStoryRequest postStoryReq, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        Story story = storyRepository.save(Story.builder()
          .title(postStoryReq.getTitle())
          .description(postStoryReq.getDescription())
          .color(postStoryReq.getColor())
          .user(user)
          .build());


        List<StoryTag> storyTagList = postStoryReq.getStoryTagList()
          .stream()
          .map(storyTagConverter::toEntity)
          .collect(Collectors.toList());

        storyTagList.forEach(storyTag -> storyTag.setStory(story));

        storyTagRepository.saveAll(storyTagList);


        List<Qna> qnaList = postStoryReq.getQnaList()
          .stream()
          .map(qnaConverter::toEntity)
          .collect(Collectors.toList());

        qnaList.forEach(qna -> qna.setStory(story));

        qnaRepository.saveAll(qnaList);

        Long newScore = user.getScore()+(Long.valueOf(qnaList.size())*10);
        user.updateScoreAndLevel(newScore, newScore/100);

        return PostStoryResponse.builder().user(user).build();
    }

    @Transactional
    public PatchBackgroundColorResponseDto patchBackgroundColor(long storyId, PatchBackgroundColorRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        Story story = storyRepository.findStoryByUserAndAndId(user, storyId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ???????????? ????????????."));

        story.updateColor(requestDto.getColor());

        return PatchBackgroundColorResponseDto.builder()
                .color(requestDto.getColor())
                .build();
    }

    // ????????? ?????? ??????
    @Transactional
    public PatchOpenStatusResponseDto patchOpen(long storyId, PatchOpenStatusRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        Story story = storyRepository.findStoryByUserAndAndId(user, storyId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ???????????? ????????????."));

        story.changeIsOpen(!requestDto.getIsOpened());

        return PatchOpenStatusResponseDto.builder()
                .isOpened(!requestDto.getIsOpened())
                .build();
    }

    @Transactional
    public PatchMainStatusResponseDto patchMain(long storyId, PatchMainStatusRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        Story story = storyRepository.findStoryByUserAndAndId(user, storyId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ???????????? ????????????."));

        story.changeIsMain(!requestDto.getIsMain());

        return PatchMainStatusResponseDto.builder()
            .isMain(!requestDto.getIsMain())
            .build();

    }

    @Transactional
    public String deleteStory(Long storyId, Long userId) {
        User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        Story story = storyRepository.findStoryByUserAndAndId(user, storyId)
          .orElseThrow(() -> new IllegalArgumentException("???????????? ????????? ???????????? ????????????."));

        storyRepository.delete(story);

        return "Story deleted.";
    }

    // ????????? ?????? ????????? (story ?????? + qnaList)
    public StorySpecDto getStorySpecPage(long storyId, long userId) {

        // ??????
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??????????????????."));

        Boolean isLikedFlag = false;

        Collection<Like> liked = likeRepository.isLiked(story, user);

        if (!liked.isEmpty()) {
            isLikedFlag = true;
        }

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreatedAt())
                .color(story.getColor())
                .likeCnt(story.getLikeCnt())
                .isLiked(isLikedFlag)
                .isOpen(story.getIsOpen())
                .isMain(story.getIsMain())
                .profileImgUrl(story.getUser().getProfileImg())
                .nickname(story.getUser().getNickname())
                .writerId(story.getUser().getId())
                .myId(userId)
                .build();

        // qna ?????????
        List<Qna> qnaList = qnaRepository.findAllByStory(story);
        List<QnaDto> qnaListDto = new ArrayList<>();
        for (Qna qnas : qnaList) {
            QnaDto dto = QnaDto.builder()
                    .question(qnas.getQuestion())
                    .tagId(qnas.getTag().getId())
                    .answer(qnas.getAnswer())
                    .build();
            qnaListDto.add(dto);
        }

        StorySpecDto storySpec = StorySpecDto.builder()
                .story(storyCover)
                .qnaList(qnaListDto)
                .build();

        return storySpec;
    }

    // tagId ?????? ?????????  ?????????
//    public List<ResponseStoryFilterDto> getFilterStoryPage(HttpServletRequest request, long tagId, Pageable pageable) {
    public StoryCountDto getFilterStoryPage(HttpServletRequest request, long tagId, Pageable pageable) {
        Long userId = (Long) request.getAttribute("id");
        List<String> setContents = new ArrayList<>();
        // story??? storyTag.Content??? List??? ?????????
        List<String> contents = setContents;
        // ?????? ?????????
        List<ResponseStoryFilterDto> dtos = new ArrayList<>();


        // userId??? tagId??? ???????????? story ??????
        List<ResponseStoryDto> stories = storyRepository.getFilterStoryPage(userId, tagId, pageable);
        stories.forEach(story -> contents.add(story.getStoryTag()));

        // ?????? ??????
        setContents = contents.stream().distinct().collect(Collectors.toList());



        // content??? ???????????? story ??????
        setContents.forEach(content -> dtos.add(ResponseStoryFilterDto.builder()
                                                                    .storyTag(content)
                                                                    .stories(stories
                                                                                    .stream()
                                                                                    .filter(story -> Objects.equals(story.getStoryTag(), content))
                                                                            .collect(Collectors.toList()))
                                                                    .build())
        );

        List<Story> allStories = storyRepository.findAllByUserId(userId);

        return StoryCountDto.builder()
                .userStoryCnt(allStories.size())
                .userPieceCnt(allStories.stream().mapToInt(qnaRepository::countByStory).sum())
                .storyTags(dtos)
                .build();

    }

    @Transactional
    public PostLikeResponseDto postLike(long storyId, PostLikeRequestDto dto, HttpServletRequest request) {
        boolean status = false;
        Long userId = (Long) request.getAttribute("id");

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("no Story"));

        if (!dto.getIsLiked()) {
            likeRepository.deleteByStoryIdAndUserId(storyId, userId);
            story.changeLikeCnt(false);
            status = false;

        } else {
            boolean exist = likeRepository.existsByUserIdAndStoryId(userId, storyId);

            if (!exist) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("no User"));

                likeRepository.save(Like.builder()
                        .story(story)
                        .user(user)
                        .build()
                );
                story.changeLikeCnt(true);
            }
            status = true;

        }



        return PostLikeResponseDto.builder()
                .isLiked(status)
                .build();
    }

    public StoryMyCountDto getMyStoryList(Pageable pageable, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        List<Story> stories = storyRepository.findAllByUserId(userId);

        List<ResponseMyStoryDto> dtos = storyRepository.findUserAllStory(pageable, userId)
                                                    .stream().map(ResponseMyStoryDto::new)
                                                    .collect(Collectors.toList());


        return StoryMyCountDto.builder()
                .userStoryCnt(stories.size())
                .userPieceCnt(stories.stream().mapToInt(qnaRepository::countByStory).sum())
                .stories(dtos)
                .build();
    }
}
