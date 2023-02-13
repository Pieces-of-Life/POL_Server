package com.umc.pol.domain.user.controller;

import com.umc.pol.domain.user.dto.PatchUserInfoRequestDto;
import com.umc.pol.domain.user.dto.PatchUserInfoResponseDto;
import com.umc.pol.domain.user.dto.MypageGetResponseDto;
import com.umc.pol.domain.user.dto.UserInfoGetResponseDto;
import com.umc.pol.domain.user.service.UserService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ResponseService responseService;
    private final UserService userService;

    // 유저 정보 조회
    @Operation(summary = "유저 정보 조회", description = "현재 유저의 정보를 조회합니다.")
    @GetMapping("/info")
    public SingleResponse<UserInfoGetResponseDto> getUserInfo(HttpServletRequest request) {

        return responseService.getSingleResponse(userService.getUserInfo(request));

    }

    @Operation(summary = "마이 페이지 조회", description = "마이페이지에서 내가 좋아요 누른 자서전과 쪽지함을 모두 조회합니다.")
    @GetMapping("/mypage")
    public SingleResponse<MypageGetResponseDto> getMypage(HttpServletRequest request) {

        return responseService.getSingleResponse(userService.getMypageInfo(request));
    }

    @Operation(summary = "유저 정보 수정", description = "현재 유저의 닉네임과 이미지를 수정합니다.")
    @PatchMapping("/profile")
    public SingleResponse<PatchUserInfoResponseDto> fetchUserInfo(@RequestParam("profileImgUrl")MultipartFile images, @RequestParam String nickname, HttpServletRequest request) {
        PatchUserInfoResponseDto patchUserInfoResponseDto = null;
        try {
            patchUserInfoResponseDto = userService.fetchUserInfo(images, request, nickname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseService.getSingleResponse(patchUserInfoResponseDto);
    }
}
