package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryService {

  private final StoryRepository storyRepository;
  private final StoryTagRepository storyTagRepository;
  private final QnaRepository qnaRepository;
  private final LikeRepository likeRepository;

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

  @Transactional
  public PatchMainStatusResponseDto patchMain(long storyId, PatchMainStatusRequestDto requestDto) {
    Story story = storyRepository.findById(storyId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

    story.changeIsMain(!requestDto.getIsPicked());


    return PatchMainStatusResponseDto.builder()
            .isPicked(!requestDto.getIsPicked())
            .build();
  }
}