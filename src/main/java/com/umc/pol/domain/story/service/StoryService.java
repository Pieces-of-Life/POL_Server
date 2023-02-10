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
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.StoryRepository;
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

    // 전체 스토리 조회
    public List<Story> getAllStory() {
        return storyRepository.findAll();
    }

    // 사용자가 좋아요한 스토리 반환
    public List<Like> getStoryByUserLike(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    public List<Like> getAllLike(){
        return likeRepository.findAll();
    }



}
