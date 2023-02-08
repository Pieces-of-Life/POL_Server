package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/story")

public class StoryController {
    private final StoryService storyService;
    private final ResponseService responseService;

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

    /*// 스토리 상세 페이지 (story 표지 + qna 목록)
    @GetMapping("/{storyId}")
    public SingleResponse<StoryCoverDto> getStorySpecPage(@PathVariable("storyId") long storyId) {
        return responseService.getSingleResponse(storyService.getStorySpecPage(storyId));
    }*/

    // 스토리 상세 페이지 (story 표지 + qna 목록)
    @GetMapping("/{storyId}")
    public SingleResponse<StorySpecDto> getStorySpecPage(@PathVariable("storyId") long storyId) {
        return responseService.getSingleResponse(storyService.getStorySpecPage(storyId));
    }
}