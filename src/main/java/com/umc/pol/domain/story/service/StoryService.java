package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
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
  private final UserRepository userRepository;

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

  @Transactional
  public PostLikeResponseDto postLike(long storyId, PostLikeRequestDto dto, long userId) {
    boolean status = false;
    if (dto.getIsLiked()){
      likeRepository.deleteByStoryIdAndUserId(storyId, userId);
    }else{

      Story story = storyRepository.findById(storyId)
              .orElseThrow(() -> new IllegalArgumentException("no Story"));

      User user =  userRepository.findById(userId)
              .orElseThrow(() -> new IllegalArgumentException("no User"));

      Like newLike = Like.builder()
              .story(story)
              .user(user)

              .build();

      likeRepository.save(newLike);
      status = true;
    }

    return PostLikeResponseDto.builder()
            .isLiked(status)
            .build();
  }
}
