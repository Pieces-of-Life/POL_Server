package com.umc.pol.domain.auth.controller;

import com.umc.pol.domain.auth.config.jwt.JwtProperties;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoJwtResponseDto;
import com.umc.pol.domain.auth.service.KakaoAuthService;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;
    private final ResponseService responseService;

    // 카카오 로그인
    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    @GetMapping(value = "/kakao", headers = "accessToken")
    public SingleResponse<KakaoJwtResponseDto> getKakaoCallback(@RequestHeader("accessToken") String accessToken) {

        KakaoJwtResponseDto jwtToken = kakaoAuthService.KakaoLogin(accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        //(4)
        return responseService.getSingleResponse(jwtToken);
    }



}
