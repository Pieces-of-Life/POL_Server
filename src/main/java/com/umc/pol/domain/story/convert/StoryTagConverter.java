package com.umc.pol.domain.story.convert;

import com.umc.pol.domain.story.dto.request.StoryTagDto;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.entity.StoryTag;
import com.umc.pol.global.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoryTagConverter {

  private final TagRepository tagRepository;

  public StoryTag toEntity(
    StoryTagDto storyTagDto
  ) {
    StoryTag storyTag = StoryTag.builder()
      .tag(tagRepository.findById(storyTagDto.getTagId()).orElseThrow())
      .content(storyTagDto.getTagContent())
      .build();

    return storyTag;
  }
}
