package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUserId(Long userId);

    Long countByStory(Story story);
}
