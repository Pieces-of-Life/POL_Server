package com.umc.pol.domain.user.service;

import com.umc.pol.domain.user.dto.UserInfoGetResponseDto;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoGetResponseDto getUserInfo(long userId) {
        User userInfo = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다"));


        UserInfoGetResponseDto userInfoGetResponseDto = UserInfoGetResponseDto.builder()
                .nickname(userInfo.getNickname())
                .score(userInfo.getScore())
                .profileImgUrl(userInfo.getProfileImg())
                .level(userInfo.getLevel())
                .build();

        return userInfoGetResponseDto;

    }

}