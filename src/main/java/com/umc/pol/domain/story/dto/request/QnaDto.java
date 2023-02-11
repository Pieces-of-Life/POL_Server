package com.umc.pol.domain.story.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이야기 Q&A")
@Getter
@NoArgsConstructor
public class QnaDto {

  @Schema(description = "Q&A 태그 종류(아이디)")
  private Long tagId;

  @Schema(description = "Q&A 질문")
  private String question;

  @Schema(description = "Q&A 답변")
  private String answer;

  @Builder
  public QnaDto(Long tagId, String question, String answer) {
    this.tagId = tagId;
    this.question = question;
    this.answer = answer;
  }

}
