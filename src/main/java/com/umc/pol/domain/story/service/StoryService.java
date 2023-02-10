package com.umc.pol.domain.story.service;

import com.umc.pol.domain.story.dto.*;
import com.umc.pol.domain.story.entity.Like;
import com.umc.pol.domain.story.entity.Qna;
import com.umc.pol.domain.story.entity.Story;
import com.umc.pol.domain.story.repository.LikeRepository;
import com.umc.pol.domain.story.repository.QnaRepository;
import com.umc.pol.domain.story.repository.StoryRepository;
import com.umc.pol.domain.story.repository.StoryTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryTagRepository storyTagRepository;
    private final QnaRepository qnaRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public PatchBackgroundColorResponseDto patchBackgroundColor(long storyId,
                                                                PatchBackgroundColorRequestDto requestDto) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 스토리입니다."));
        story.updateColor(requestDto.getColor());

        return PatchBackgroundColorResponseDto.builder()
                .color(requestDto.getColor())
                .build();
    }

    // 이야기 공개 설정
    @Transactional
    public PatchOpenStatusResponseDto patchOpen(long storyId, PatchOpenStatusRequestDto requestDto) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        story.changeIsOpen(!requestDto.getIsOpened());

        return PatchOpenStatusResponseDto.builder()
                .isOpened(!requestDto.getIsOpened())
                .build();
    }

    @Transactional
    public PatchMainStatusResponseDto patchMain(long storyId, PatchMainStatusRequestDto requestDto) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        story.changeIsMain(!requestDto.getIsMain());


        return PatchMainStatusResponseDto.builder()
                .isMain(!requestDto.getIsMain())
                .build();
    }

    // 전체 스토리 조회
    public List<Story> getAllStory() {
        return storyRepository.findAll();
    }

    // 사용자가 좋아요한 스토리 반환
    public List<Like> getStoryByUserLike(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    public List<Like> getAllLike() {
        return likeRepository.findAll();
    }

    // 쪽지 상세 페이지 (story 표지 + qnaList)
    public StorySpecDto getStorySpecPage(long storyId) {

        // 표지
        // 이 부분 머지 후 경민님이 만들어둔 GetStoryResponse 로 바꾸기
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스토리입니다."));

        Long likeCnt = story.getLikes().stream().count(); // ??
        System.out.println("likeCnt = " + likeCnt);

        StoryCoverDto storyCover = StoryCoverDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .date(story.getCreated_at())
                .color(story.getColor())
                .likeCnt(likeCnt)
                .profileImgUrl("https://image.msscdn.net/images/goods_img/20201104/1678629/1678629_1_500.jpg")
                .nickname("임시이름")
                .build();


        // qna 리스트
//        List<Qna> qnaList = qnaRepository.findAll();
        List<Qna> qnaList = qnaRepository.findByStory(story); // 전부 불러오는 것 같음, 수정 필요
        List<QnaDto> qnaListDto = new ArrayList<>();
        for (Qna qnas : qnaList) {
            QnaDto dto = QnaDto.builder()
                    .id(qnas.getId())
                    .question(qnas.getQuestion())
                    .tagCategoryId(qnas.getTag().getId())
                    .answer(qnas.getAnswer())
                    .build();
            qnaListDto.add(dto);
        }

        StorySpecDto storySpec = StorySpecDto.builder()
                .story(storyCover)
                .qnaList(qnaListDto)
                .build();

        return storySpec;
    }
}