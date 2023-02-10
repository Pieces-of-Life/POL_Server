package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryTagRepository storyTagRepository;
    private final QnaRepository qnaRepository;
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
        List<Qna> qnaList = qnaRepository.findAllByStory(story); // 전부불러오는 것 같음, 수정 필요
        List<QnaDto> qnaListDto = new ArrayList<>();
        for (Qna qnas : qnaList) {
            QnaDto dto = QnaDto.builder()
                    .id(qnas.getId())
                    .question(qnas.getQuestion())
                    .tagCategoryId(qnas.getTag().getId())
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
}
