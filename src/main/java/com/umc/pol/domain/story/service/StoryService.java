package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.StoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryService {

  private final StoryRepository storyRepository;

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
                                                              PatchBackgroundColorRequestDto requestDto){
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

  public String deleteStory(Long storyId) {
    storyRepository.deleteById(storyId);

    return "Story deleted.";
  }
}
