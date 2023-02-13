package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.*;

import com.umc.pol.domain.story.dto.request.PostStoryRequest;
import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.dto.response.PostStoryResponse;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ListResponse;
import com.umc.pol.global.response.ResponseService;

import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/story")
@Tag(name = "Story", description = "이야기 API")
public class StoryController {

  private final StoryService storyService;
  private final ResponseService responseService;

  @GetMapping("")
  @Operation(summary = "전체 공개 이야기 목록 조회 API", description = "")
  public ListResponse<GetStoryResponse> getStoryList(
    @RequestParam Long cursorId, Pageable pageable
  ) {

    return responseService.getListResponse(storyService.getStoryList(pageable, cursorId));
  }

  @GetMapping("/main")
  @Operation(summary = "사용자 대표 이야기 목록 조회 API", description = "")
  public ListResponse<GetStoryResponse> getUserMainStoryList(
    HttpServletRequest request,
    @RequestParam Long cursorId, Pageable pageable
  ) {

    return responseService.getListResponse(storyService.getUserMainStoryList(pageable, cursorId, (Long) request.getAttribute("id")));
  }

  @PostMapping("")
  @Operation(summary = "이야기 생성 API", description = "")
  public SingleResponse<PostStoryResponse> postStory(
    HttpServletRequest request,
    @Valid @RequestBody PostStoryRequest postStoryReq
    ) {

    return responseService.getSingleResponse(storyService.postStory(postStoryReq, (Long) request.getAttribute("id")));
  }

  @Operation(summary = "표지색 설정", description = "이야기의 배경 색을 지정합니다.")
  @PatchMapping("/{storyId}/color")
  public SingleResponse<PatchBackgroundColorResponseDto> patchBackgroundColor(
    HttpServletRequest request,
    @PathVariable long storyId,
    @RequestBody PatchBackgroundColorRequestDto requestDto
  ) {

    return responseService.getSingleResponse(storyService.patchBackgroundColor(
      storyId,
      requestDto,
      (Long) request.getAttribute("id")
    ));
  }

  @Operation(summary = "이야기 공개 설정", description = "이야기의 공개 여부를 변경합니다.")
  @PatchMapping("/{storyId}/open")
  public SingleResponse<PatchOpenStatusResponseDto> patchOpen(
    HttpServletRequest request,
    @PathVariable long storyId,
    @RequestBody PatchOpenStatusRequestDto requestDto
  ) {
    System.out.println("open: " + requestDto.getIsOpened());

    return responseService.getSingleResponse(storyService.patchOpen(
      storyId,
      requestDto,
      (Long) request.getAttribute("id")
    ));
  }

  @Operation(summary = "대표 이야기 설정", description = "이야기의 대표 여부를 변경합니다.")
  @PatchMapping("/{storyId}/main")
  public SingleResponse<PatchMainStatusResponseDto> patchMain(
    HttpServletRequest request,
    @PathVariable long storyId,
    @RequestBody PatchMainStatusRequestDto requestDto
  ){

    return responseService.getSingleResponse(storyService.patchMain(
      storyId,
      requestDto,
      (Long) request.getAttribute("id")
    ));
  }

  @DeleteMapping("/{storyId}")
  @Operation(summary = "이야기 삭제 API", description = "")
  public SingleResponse<String> deleteStory(
    HttpServletRequest request,
    @PathVariable Long storyId
  ) {

    return responseService.getSingleResponse(storyService.deleteStory(
      storyId,
      (Long) request.getAttribute("id")
    ));
  }

  // 스토리 상세 페이지 (story 표지 + qna 목록)
  @Operation(summary = "스토리 상세 페이지 조회", description = "스토리 상세 페이지 조회 (story 표지 + qna 목록)")
  @GetMapping("/{storyId}")
  public SingleResponse<StorySpecDto> getStorySpecPage(@PathVariable("storyId") long storyId) {
    return responseService.getSingleResponse(storyService.getStorySpecPage(storyId));
  }

  @Operation(summary = "이야기 필터링", description = "자신이 쓴 이야기를 tagId를 기준으로 필터링합니다. [요청할 때마다 page를 1씩 증가시키면서 호출]")
  @GetMapping("/filter/{tagId}")
  public ListResponse<ResponseStoryFilterDto> filteringStory(@PathVariable long tagId, Pageable pageable, HttpServletRequest request) {


    return responseService.getListResponse(storyService.getFilterStoryPage(request, tagId, pageable));
  }

  @Operation(summary = "이야기 좋아요", description = "이야기에 좋아요를 남깁니다. (토큰 설정 전까지 userId를 RequestParam으로 받음.)")
  @PostMapping("/{storyId}/like")
  public SingleResponse<PostLikeResponseDto> postLike(@PathVariable long storyId, @RequestBody PostLikeRequestDto dto, HttpServletRequest request){

    return responseService.getSingleResponse(storyService.postLike(storyId, dto, request));
  }
}

