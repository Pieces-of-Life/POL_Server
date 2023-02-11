package com.umc.pol.domain.story.dto.response;

import com.umc.pol.domain.story.entity.Story;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "이야기 목록 조회")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetStoryResponse {

  @Schema(description = "이야기 ID")
  private Long id;

  @Schema(description = "이야기 제목")
  private String title;

  @Schema(description = "이야기 한 줄 설명")
  private String description;

  @Schema(description = "이야기 표지 색")
  private String color;

  @Schema(description = "이야기 작성자 닉네임")
  private String nickname;

  @Schema(description = "이야기 작성자 프로필")
  private String profileImgUrl;

  @Schema(description = "이야기 좋아요 수")
  private Long likeCnt;

  @Schema(description = "이야기 생성일")
  private String createdDate;

  @Builder
  public GetStoryResponse(Story story) {
    this.id = story.getId();
    this.title = story.getTitle();
    this.description = story.getDescription();
    this.color = story.getColor();
    this.nickname = story.getUser().getNickname();
    this.profileImgUrl = story.getUser().getProfileImg();
    this.likeCnt = story.getLikeCnt();
    this.createdDate = story.getCreatedAt().toString();
  }

}
