package com.umc.pol.domain.auth.controller;

import com.umc.pol.domain.auth.dto.KakaoAccountDto;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoTokenResponseDto;
import com.umc.pol.domain.auth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;

    // 카카오 로그인
    @GetMapping("/kakao")
    public KakaoInfoResponseDto getKakaoCallback(@RequestParam("code") String code){
        System.out.println("code = " + code);
        return kakaoAuthService.KakaoLogin(code);
    }


}
