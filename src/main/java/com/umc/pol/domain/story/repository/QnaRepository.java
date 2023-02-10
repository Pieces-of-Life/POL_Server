package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByStory(Story story);
}
