package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByStory(Story story);

    /*@Query(value = "SELECT COUNT(*) from Like where user_id = :storyId")
    public int countByStoryId(@Param("storyId") int storyId);*/

}
