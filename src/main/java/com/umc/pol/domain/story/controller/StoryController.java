package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


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

    @Operation(summary = "이야기 좋아요", description = "이야기에 좋아요를 남깁니다. (토큰 설정 전까지 userId를 RequestParam으로 받음.)")
    @PostMapping("/{storyId}/like")
    public SingleResponse<PostLikeResponseDto> postLike(@PathVariable long storyId, @RequestBody PostLikeRequestDto dto, @RequestParam("userId") long userId){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User userDetails = (User) principal;
//        long userId = userDetails.getId();

        return responseService.getSingleResponse(storyService.postLike(storyId, dto, userId));
    }

}
