package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ListResponse;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchOpenStatusResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/story")
@SecurityRequirement(name = "Bearer")
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

}
