package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.dto.ResponseStoryDto;
import java.util.List;
import com.umc.pol.domain.story.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoryRepositoryCustom {
  Page<Story> findStory(Pageable pageable, Long cursorId);
  List<ResponseStoryDto> getFilterStoryPage(long userId, long tagId, Pageable pageable);

}
