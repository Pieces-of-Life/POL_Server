package com.umc.pol.domain.user.controller;

import com.umc.pol.domain.user.dto.MypageGetResponseDto;
import com.umc.pol.domain.user.dto.UserInfoGetResponseDto;
import com.umc.pol.domain.user.repository.UserRepository;
import com.umc.pol.domain.user.service.UserService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
