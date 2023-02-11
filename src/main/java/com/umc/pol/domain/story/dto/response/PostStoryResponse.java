package com.umc.pol.domain.story.dto.response;

import com.umc.pol.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이야기 생성 후 사용자 현황")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostStoryResponse {

  @Schema(description = "사용자 점수")
  private Long score;

  @Schema(description = "사용자 레벨")
  private Long level;

  @Builder
  public PostStoryResponse(User user) {
    this.score = user.getScore();
    this.level = user.getLevel();
  }

}
