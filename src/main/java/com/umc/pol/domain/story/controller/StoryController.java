package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.response.GetStoryResponse;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ListResponse;
import com.umc.pol.global.response.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

}
