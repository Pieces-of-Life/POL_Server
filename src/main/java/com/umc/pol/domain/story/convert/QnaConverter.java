package com.umc.pol.domain.story.convert;

import com.umc.pol.domain.story.dto.request.QnaDto;
import com.umc.pol.domain.story.dto.request.StoryTagDto;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.StoryTag;
import com.umc.pol.global.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QnaConverter {

  private final TagRepository tagRepository;

  public Qna toEntity(
    QnaDto qnaDto
  ) {
    Qna qna = Qna.builder()
      .tag(tagRepository.findById(qnaDto.getTagId()).orElseThrow())
      .question(qnaDto.getQuestion())
      .answer(qnaDto.getAnswer())
      .build();

    return qna;
  }
}
