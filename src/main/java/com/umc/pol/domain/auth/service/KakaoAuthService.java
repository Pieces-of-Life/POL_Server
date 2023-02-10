package com.umc.pol.domain.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.umc.pol.domain.auth.client.KakaoAuthClient;
import com.umc.pol.domain.auth.config.jwt.JwtProperties;
import com.umc.pol.domain.auth.dto.KakaoAccountDto;
import com.umc.pol.domain.auth.dto.KakaoInfoResponseDto;
import com.umc.pol.domain.auth.dto.KakaoJwtResponseDto;
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
import java.util.Date;
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
    public KakaoJwtResponseDto KakaoLogin(String accessToken) {
        // 엑세스 토큰 요청
        //final KakaoTokenResponseDto accessToken = getAccessToken(code);

        // 사용자 정보 가져오기
        KakaoAccountDto kakaoUserInfo = getInfo(accessToken);

        // 가입 안한 경우 회원가입
        User userInfo = createUserInfoIfNeed(kakaoUserInfo);

        // jwt 추가
        String jwtToken = createToken(userInfo);

        return KakaoJwtResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }


    // 사용자 정보 가져오기
    public KakaoAccountDto getInfo(String accessToken) {
        KakaoAccountDto userInfo = null;
        try {
            userInfo = client.getInfo(new URI("https://kapi.kakao.com/v2/user/me"), "Bearer " + accessToken);
        } catch (Exception e) {
            System.out.println("error..." + e);
            return KakaoAccountDto.fail();
        }
        System.out.println("user Info -----" + userInfo.getKakaoAccount().toString());
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
    public User createUserInfoIfNeed(KakaoAccountDto userInfo) {
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getKakaoAccount().getProfile().getNickname();
        String profileImageUrl = userInfo.getKakaoAccount().getProfile().getProfileImageUrl();
        String email = userInfo.getKakaoAccount().getEmail();

        User kakaoUser = userRepository.findByKakaoId(kakaoId);

        if (kakaoUser == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            User newUser = User.builder()
                    .kakaoId(kakaoId)
                    .nickname(nickname)
                    .profileImg(profileImageUrl)
                    .password(encodedPassword)
                    .email(email)
                    .build();

            return userRepository.save(newUser);
        }

        return kakaoUser;
    }


    public String createToken(User kakaoUser){
        //JWT 생성 후 헤더에 추가해서 보내주기
        String jwtToken = JWT.create()
                .withSubject(kakaoUser.getNickname())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", kakaoUser.getId())
                .withClaim("nickname", kakaoUser.getNickname())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return jwtToken;
    }

    // 자동로그인
    public Authentication loginUser(User userInfo) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.getId(), userInfo.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

}

