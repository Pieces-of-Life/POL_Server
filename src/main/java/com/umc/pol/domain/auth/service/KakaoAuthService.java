package com.umc.pol.domain.auth.service;

import com.umc.pol.domain.auth.client.KakaoAuthClient;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoTokenResponseDto;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class KakaoAuthService {

    private final KakaoAuthClient client;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Value("${client-id}")
    private String cliendId;


    @Value("${redirect-uri}")
    private String redirectUri; // 배포 url


    //** 전체 로직 **//
    public KakaoInfoResponseDto KakaoLogin(final String code) {
        // 엑세스 토큰 요청
        final KakaoTokenResponseDto accessToken = getAccessToken(code);

        // 사용자 정보 가져오기
        KakaoInfoResponseDto kakaoUserInfo = getInfo(accessToken);

        // 가입 안한 경우 회원가입
        User userInfo = createUserInfoIfNeed(kakaoUserInfo);

        // 가입한 경우 (로그인 처리)
        Authentication authentication = loginUser(userInfo);

        return kakaoUserInfo;
    }


    // 사용자 정보 가져오기
    public KakaoInfoResponseDto getInfo(KakaoTokenResponseDto accessToken) {
        KakaoInfoResponseDto userInfo = null;
        try {
            userInfo = client.getInfo(new URI("https://kapi.kakao.com/v2/user/me"), accessToken.getTokenType() + " " + accessToken.getAccessToken());
        } catch (Exception e) {
            System.out.println("error..." + e);
            return KakaoInfoResponseDto.fail();
        }

        return userInfo;
    }

    // AccessToken Get
    public KakaoTokenResponseDto getAccessToken(final String code) {
        try {
            return client.getToken(new URI("https://kauth.kakao.com/oauth/token"), cliendId, "http://localhost:8080/auth/kakao", code, "authorization_code");
        } catch (Exception e) {
            System.out.println("error..." + e);
            return KakaoTokenResponseDto.fail();
        }
    }


    // 회원가입 (필요한 경우)
    public User createUserInfoIfNeed(KakaoInfoResponseDto userInfo) {
        Long userId = userInfo.getKakaoAccountDto().getId();
        String nickname = userInfo.getKakaoAccountDto().getKakaoAccount().getProfile().getNickname();
        String profileImageUrl = userInfo.getKakaoAccountDto().getKakaoAccount().getProfile().getProfileImageUrl();

        User kakaoUser = userRepository.findById(userId).orElse(null);

        if (kakaoUser == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            User newUser = User.builder()
                    .id(userId)
                    .nickname(nickname)
                    .profileImg(profileImageUrl)
                    .password(encodedPassword)
                    .build();

            return userRepository.save(newUser);
        }
        return kakaoUser;
    }

    // 자동로그인
    public Authentication loginUser(User userInfo) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getId(), userInfo.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

}

