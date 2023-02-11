package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.dto.request.PostStoryRequest;
import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.response.PostStoryResponse;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.StoryTag;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.global.response.ListResponse;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/story")
@Tag(name = "Story", description = "이야기 API")
public class StoryController {

  private final StoryService storyService;
  private final ResponseService responseService;

  @GetMapping("")
  @Operation(summary = "전체 공개 이야기 목록 조회 API", description = "")
  public ListResponse<GetStoryResponse> getStoryList(@RequestParam Long cursorId, Pageable pageable) {

    return responseService.getListResponse(storyService.getStoryList(pageable, cursorId));
  }

  @GetMapping("/main")
  @Operation(summary = "사용자 대표 이야기 목록 조회 API", description = "")
  public ListResponse<GetStoryResponse> getUserMainStoryList(@RequestParam Long cursorId, Pageable pageable, Long userId) {

    return responseService.getListResponse(storyService.getUserMainStoryList(pageable, cursorId, userId));
  }

  @PostMapping("")
  @Operation(summary = "이야기 생성 API", description = "")
  public SingleResponse<PostStoryResponse> postStory(
    @RequestParam String userId,

    @Valid
    @RequestBody
      PostStoryRequest postStoryReq
    ) {

    return responseService.getSingleResponse(storyService.postStory(postStoryReq, userId));
  }

  @Operation(summary = "표지색 설정", description = "이야기의 배경 색을 지정합니다.")
  @PatchMapping("/{storyId}/color")
  public SingleResponse<PatchBackgroundColorResponseDto> patchBackgroundColor(@PathVariable long storyId,
                                                                              @RequestBody PatchBackgroundColorRequestDto requestDto) {

    return responseService.getSingleResponse(storyService.patchBackgroundColor(storyId, requestDto));
  }

  @Operation(summary = "이야기 공개 설정", description = "이야기의 공개 여부를 변경합니다.")
  @PatchMapping("/{storyId}/open")
  public SingleResponse<PatchOpenStatusResponseDto> patchOpen(@PathVariable long storyId, @RequestBody PatchOpenStatusRequestDto requestDto) {
    System.out.println("open: " + requestDto.getIsOpened());

    return responseService.getSingleResponse(storyService.patchOpen(storyId, requestDto));
  }

  @Operation(summary = "대표 이야기 설정", description = "이야기의 대표 여부를 변경합니다.")
  @PatchMapping("/{storyId}/main")
  public SingleResponse<PatchMainStatusResponseDto> patchMain(@PathVariable long storyId, @RequestBody PatchMainStatusRequestDto requestDto){

    return responseService.getSingleResponse(storyService.patchMain(storyId, requestDto));
  }

  @DeleteMapping("/{storyId}")
  @Operation(summary = "이야기 삭제 API", description = "")
  public SingleResponse<String> deleteStory(@PathVariable long storyId) {

    return responseService.getSingleResponse(storyService.deleteStory(storyId));
  }

  // 스토리 상세 페이지 (story 표지 + qna 목록)
  @Operation(summary = "스토리 상세 페이지 조회", description = "스토리 상세 페이지 조회 (story 표지 + qna 목록)")
  @GetMapping("/{storyId}")
  public SingleResponse<StorySpecDto> getStorySpecPage(@PathVariable("storyId") long storyId) {
    return responseService.getSingleResponse(storyService.getStorySpecPage(storyId));
  }
}