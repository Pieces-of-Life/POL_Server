package com.umc.pol.domain.story.repository;

import com.umc.pol.domain.story.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
}
