package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.dto.ResponseStoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoryRepositoryCustom {
    List<ResponseStoryDto> getFilterStoryPage(long userId, long tagId, Pageable pageable);
}
