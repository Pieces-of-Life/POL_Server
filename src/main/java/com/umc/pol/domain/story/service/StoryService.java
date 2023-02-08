package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryTagRepository storyTagRepository;
    private final QnaRepository qnaRepository;
    private final LikeRepository likeRepository;


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

        story.changeIsMain(!requestDto.getIsPicked());


        return PatchMainStatusResponseDto.builder()
                .isPicked(!requestDto.getIsPicked())
                .build();
    }

    // list를 dto로 반환 : https://velog.io/@nyong_i/List를-Dto로-반환하는-방법-RESTful-API

    /*// 쪽지 상세 페이지 (story 표지 + chatList)
    public StoryCoverDto getStorySpecPage(long storyId) {

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

//        Long likeCnt = likeRepository.countByStory(story);

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreated_at())
                .color(story.getColor())
                .likeCnt(0L)
                .profileImgUrl("https://image.msscdn.net/images/goods_img/20201104/1678629/1678629_1_500.jpg")
                .nickname("임시이름")
                .build();

        return storyCover;
    }*/

    // 쪽지 상세 페이지 (story 표지 + chatList)
    public StorySpecDto getStorySpecPage(long storyId) {

        // 표지
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        // Long likeCnt = likeRepository.countByStory(story);

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreated_at())
                .color(story.getColor())
                .likeCnt(0L)
                .profileImgUrl("https://image.msscdn.net/images/goods_img/20201104/1678629/1678629_1_500.jpg")
                .nickname("임시이름")
                .build();

        List<Qna> all = qnaRepository.findAll();

        // qna 리스트
        // List<Qna> qnaLists = qnaRepository.findAll();
        /*List<QnaDto> qnaListDto = new ArrayList<>();

        for (Qna qnas : qnaList) {
            QnaDto dto = QnaDto.builder()
                    .id(qnas.getId())
                    .question(qnas.getQuestion())
                    .tagCategoryId(1)
                    .answer(qnas.getAnswer())
                    .build();

            qnaListDto.add(dto);
        }*/

        StorySpecDto storySpec = StorySpecDto.builder()
                .story(storyCover)
                .qnaList(all)
                .build();

        return storySpec;
    }
}