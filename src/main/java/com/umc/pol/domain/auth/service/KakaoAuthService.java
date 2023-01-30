package com.umc.pol.domain.auth.service;
import com.umc.pol.domain.auth.client.KakaoAuthClient;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class KakaoAuthService {

    private final KakaoAuthClient client;

    @Value("${kakao-token-url}")
    private String kakaoAuthUrl;

    @Value("${kakao-user-api-url}")
    private String kakaoUserApiUrl;

    @Value("${client-id}")
    private String cliendId;

    @Value("${redirect-local-uri}")
    private String redirectLocalUri; // 로컬 url

    @Value("${redirect-uri}")
    private String redirectUri; // 배포 url

    public KakaoInfoResponseDto getInfo(final String code) {
        final KakaoTokenResponseDto token = getAccessToken(code);
        System.out.println("token = " + token);

        try {
            return client.getInfo(new URI(kakaoUserApiUrl), token.getTokenType() + " " + token.getAccessToken());
        } catch (Exception e){
            System.out.println("error..." + e);
            return KakaoInfoResponseDto.fail();
        }

    }

    private KakaoTokenResponseDto getAccessToken(final String code){
        try{
            return client.getToken(new URI(kakaoAuthUrl), cliendId, redirectLocalUri,code,"authorization_code");
        } catch(Exception e){
            System.out.println("error..." + e);
            return KakaoTokenResponseDto.fail();
        }
    }
}
