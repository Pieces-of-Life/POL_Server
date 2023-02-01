package com.umc.pol.domain.auth.client;

import com.umc.pol.domain.auth.config.KakaoFeignConfiguration;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "kakaoAuthClient", configuration = KakaoFeignConfiguration.class)
public interface KakaoAuthClient {
    @PostMapping
    KakaoTokenResponseDto getToken(URI baseUrl, @RequestParam("client_id") String clientId,
                                   @RequestParam("redirect_uri") String redirectUrl,
                                   @RequestParam("code") String code,
                                   @RequestParam("grant_type") String grantType);

    @PostMapping
    KakaoInfoResponseDto getInfo(URI baseUrl, @RequestHeader("Authorization") String accessToken);


}
