package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.convert.QnaConverter;
import com.umc.pol.domain.story.convert.StoryTagConverter;
import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.dto.request.PostStoryRequest;
import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.response.PostStoryResponse;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.entity.StoryTag;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;

import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;

import java.util.ArrayList;
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
    public PatchBackgroundColorResponseDto patchBackgroundColor(long storyId,
                                                                PatchBackgroundColorRequestDto requestDto) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 스토리입니다."));
        story.updateColor(requestDto.getColor());

        return PatchBackgroundColorResponseDto.builder()
                .color(requestDto.getColor())
                .build();
    }

    // 이야기 공개 설정
    @Transactional
    public PatchOpenStatusResponseDto patchOpen(long storyId, PatchOpenStatusRequestDto requestDto) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        story.changeIsOpen(!requestDto.getIsOpened());

        return PatchOpenStatusResponseDto.builder()
                .isOpened(!requestDto.getIsOpened())
                .build();
    }

    @Transactional
    public PatchMainStatusResponseDto patchMain(long storyId, PatchMainStatusRequestDto requestDto) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

    story.changeIsMain(!requestDto.getIsMain());


    return PatchMainStatusResponseDto.builder()
            .isMain(!requestDto.getIsMain())
            .build();
    }

  public String deleteStory(Long storyId) {
    storyRepository.deleteById(storyId);

        return "Story deleted.";
    }

    // 쪽지 상세 페이지 (story 표지 + qnaList)
    public StorySpecDto getStorySpecPage(long storyId) {

        // 표지
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

        // qna 리스트
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

    // tagId 기준 이야기  필터링
    public List<ResponseStoryFilterDto> getFilterStoryPage(HttpServletRequest request, long tagId, Pageable pageable) {
        Long userId = (Long) request.getAttribute("id");
        System.out.println("ㅇㅇ:" + userId);
        List<String> setContents = new ArrayList<>();
        // story의 storyTag.Content를 List로 만들기
        List<String> contents = setContents;
        // 반환 리스트
        List<ResponseStoryFilterDto> dtos = new ArrayList<>();


        // userId와 tagId를 기반으로 story 검색
        List<ResponseStoryDto> stories = storyRepository.getFilterStoryPage(userId, tagId, pageable);
        stories.forEach(story -> contents.add(story.getStoryTag()));

        // 중복 제거
        setContents = contents.stream().distinct().collect(Collectors.toList());


        // content를 기준으로 story 묶기
        setContents.forEach(content -> dtos.add(ResponseStoryFilterDto.builder()
                                                                    .storyTag(content)
                                                                    .stories(stories
                                                                                    .stream()
                                                                                    .filter(story -> Objects.equals(story.getStoryTag(), content))
                                                                            .collect(Collectors.toList()))
                                                                    .build())
        );

        return dtos;
    }

}
