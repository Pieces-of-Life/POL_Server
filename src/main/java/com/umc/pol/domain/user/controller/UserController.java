package com.umc.pol.domain.user.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ResponseService responseService;
    private final UserService userService;

    // 유저 정보 조회
    @Operation(summary = "유저 정보 조회", description = "현재 유저의 정보를 조회합니다.")
    @GetMapping("/info")
    public SingleResponse<UserInfoGetResponseDto> getName() {
        // 현재 사용자의 ID return
        /*
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userDetails = (User) principal;
        long userId = userDetails.getId();
        String password = userDetails.getPassword();
        */
        long userId = 5;

        return responseService.getSingleResponse(userService.getUserInfo(userId));

    }
}
