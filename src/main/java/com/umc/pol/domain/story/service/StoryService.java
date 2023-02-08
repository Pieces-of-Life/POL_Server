package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.repository.StoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

}
