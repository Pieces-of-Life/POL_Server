package com.umc.pol.domain.auth.controller;

import com.umc.pol.domain.auth.dto.KakaoAccountDto;
import com.umc.pol.domain.auth.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/kakao/callback")
    public KakaoAccountDto getKakaoCallback(@RequestParam("code") String code){
        System.out.println("code = " + code);
        return kakaoAuthService.getInfo(code).getKakaoAccountDto();
    }
}
