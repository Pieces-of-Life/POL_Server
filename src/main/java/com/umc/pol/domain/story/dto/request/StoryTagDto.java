package com.umc.pol.domain.story.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이야기 태그")
@Getter
@NoArgsConstructor
public class StoryTagDto {

  @Schema(description = "이야기 태그 종류(아이디)")
  private Long tagId;

  @Schema(description = "이야기 태그 내용")
  private String tagContent;

  @Builder
  public StoryTagDto(Long tagId, String tagContent){
    this.tagId = tagId;
    this.tagContent = tagContent;
  }

}
