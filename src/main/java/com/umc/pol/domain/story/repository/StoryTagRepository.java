package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.StoryTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryTagRepository extends JpaRepository<StoryTag, Long> {
}
