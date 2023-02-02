package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.PatchBackgroundColorRequestDto;
import com.umc.pol.domain.story.dto.PatchBackgroundColorResponseDto;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
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
}
