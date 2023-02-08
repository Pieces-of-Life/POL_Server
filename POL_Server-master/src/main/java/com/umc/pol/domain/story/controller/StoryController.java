package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.dto.PatchMainStatusRequestDto;
import com.umc.pol.domain.story.dto.PatchMainStatusResponseDto;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "대표 이야기 설정", description = "이야기의 대표 여부를 변경합니다.")
    @PatchMapping("/{storyId}/main")
    public SingleResponse<PatchMainStatusResponseDto> patchMain(@PathVariable long storyId, @RequestBody PatchMainStatusRequestDto requestDto){

        return responseService.getSingleResponse(storyService.patchMain(storyId, requestDto));
    }
}
