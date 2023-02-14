package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>, StoryRepositoryCustom {
  Optional<Story> findStoryByUserAndAndId(User user, Long id);
}
