package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
    boolean existsByUserIdAndStoryId(Long userId, long storyId);
    void deleteByStoryIdAndUserId(long storyId, long userId);

    @Query("select l from Like l where l.story = :story and l.user = :user")
    List<Like> isLiked(@Param("story") Story story, @Param("user") User user);

}
