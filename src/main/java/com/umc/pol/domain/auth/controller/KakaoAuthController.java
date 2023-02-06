package com.umc.pol.domain.auth.controller;

import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;

    // 카카오 로그인
    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    @GetMapping("/kakao")
    public KakaoInfoResponseDto getKakaoCallback(@RequestParam("code") String code) {
        System.out.println("code = " + code);
        return kakaoAuthService.KakaoLogin(code);
    }


}
