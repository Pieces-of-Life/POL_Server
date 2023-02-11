package com.umc.pol.domain.story.dto.request;

import com.umc.pol.domain.story.entity.Story;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이야기 생성 요청")
@Getter
@NoArgsConstructor
public class PostStoryRequest {

  @Schema(description = "이야기 제목")
  private String title;

  @Schema(description = "이야기 한 줄 설명")
  private String description;

  @Schema(description = "이야기 표지 색")
  private String color;

  @Schema(description = "이야기 태그 리스트")
  private List<StoryTagDto> storyTagList;

  @Schema(description = "이야기 Q&A 리스트")
  private List<QnaDto> qnaList;

  @Builder
  public PostStoryRequest(String title, String description, String color, List<StoryTagDto> storyTagList, List<QnaDto> qnaList) {
    this.title = title;
    this.description = description;
    this.color = color;
    this.storyTagList = storyTagList;
    this.qnaList = qnaList;
  }

  public Story toEntity() {
    return Story.builder().title(title).description(description).color(color).build();
  }

}
