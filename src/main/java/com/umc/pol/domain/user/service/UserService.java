package com.umc.pol.domain.user.service;

import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.service.StoryService;
import com.umc.pol.domain.user.dto.MypageChatDto;
import com.umc.pol.domain.user.dto.MypageGetResponseDto;
import com.umc.pol.domain.user.dto.MypageLikeStoryDto;
import com.umc.pol.domain.user.dto.UserInfoGetResponseDto;
import com.umc.pol.domain.user.entity.User;
import com.umc.pol.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final StoryService storyService;

    // 유저 정보 조회
    public UserInfoGetResponseDto getUserInfo(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("id");

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

    // 마이페이지 조회
    public MypageGetResponseDto getMypageInfo(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("id");

        User userInfo = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다"));

        List<Story> allStoryData = storyService.getAllStory();
        List<Like> userLikeData = storyService.getStoryByUserLike(userId);
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
                            .date(storyData.getCreated_at())
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

}