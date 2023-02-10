package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByStoryIdAndUserId(long storyId, long userId);
    List<Like> findByUserId(Long userId);

    boolean existsByUserIdAndStoryId(Long userId, long storyId);
}
