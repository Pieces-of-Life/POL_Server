package com.umc.pol.domain.user.service;

import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.user.dto.*;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
import com.umc.pol.global.client.S3StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final LikeRepository likeRepository;
    private final S3StorageClient s3StorageClient;

    // 유저 정보 조회
    public UserInfoGetResponseDto getUserInfo(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("id");

        User userInfo = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다"));


        UserInfoGetResponseDto userInfoGetResponseDto = UserInfoGetResponseDto.builder()
                .userId(userInfo.getId())
                .nickname(userInfo.getNickname())
                .score(userInfo.getScore())
                .profileImgUrl(userInfo.getProfileImg())
                .level(userInfo.getLevel())
                .build();

        return userInfoGetResponseDto;

    }

    // 마이페이지 조회
    public MypageGetResponseDto getMypageInfo(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("id");

        User userInfo = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다"));

        List<Story> allStoryData = storyRepository.findAll();
        List<Like> userLikeData = likeRepository.findByUserId(userId);
        List<MypageLikeStoryDto> mypageLikeStoryDtoList = new ArrayList<MypageLikeStoryDto>();

        // 사용자가 좋아요 한 스토리 리스트 조회하기
        userLikeData.forEach(like -> {
            // 전체 스토리에서 좋아요한 스토리 찾기
            Optional<Story> storyStream = allStoryData.stream().filter(story -> story.getId().equals(like.getStory().getId())).findFirst();
            if (storyStream.isPresent()) {
                Optional<User> friendStream = userRepository.findById(storyStream.get().getUser().getId());
                if (friendStream.isPresent()) {
                    Story storyData = storyStream.get();
                    User userData = friendStream.get();
                    MypageLikeStoryDto mypageLikeStoryDto = MypageLikeStoryDto.builder()
                            .id(storyData.getId())
                            .title(storyData.getTitle())
                            .description(storyData.getDescription())
                            .color(storyData.getColor())
                            .date(storyData.getCreatedAt())
                            .userId(userData.getId())
                            .profileImgUrl(userData.getProfileImg())
                            .nickname(userData.getNickname())
                            .build();
                    mypageLikeStoryDtoList.add(mypageLikeStoryDto);
                }
            }

        });
        List<MypageChatDto> mypageChatDtoList = new ArrayList<MypageChatDto>();

        return MypageGetResponseDto.builder()
                .story(mypageLikeStoryDtoList)
                .chat(mypageChatDtoList)
                .build();

    }

    @Transactional
    public PatchUserInfoResponseDto fetchUserInfo(MultipartFile images, HttpServletRequest request, String nickname) throws IOException {
        Long userId = (Long) request.getAttribute("id");

        User userInfo = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다"));

        // get profileImageUrl
        String uploadUrl = s3StorageClient.upload(images);

        // 유저데이터 업데이트
        userInfo.updateUserInfo(uploadUrl, nickname);

        return PatchUserInfoResponseDto.builder()
                .profileImgUrl(uploadUrl)
                .nickname(nickname)
                .build();

    }

}
