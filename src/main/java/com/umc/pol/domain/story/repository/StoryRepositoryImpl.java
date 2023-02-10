package com.umc.pol.domain.story.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.pol.domain.story.dto.QResponseStoryDto;
import com.umc.pol.domain.story.dto.ResponseStoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.umc.pol.domain.story.entity.QStory.story;
import static com.umc.pol.domain.story.entity.QStoryTag.storyTag;

@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ResponseStoryDto> getFilterStoryPage(long userId, long tagId, Pageable pageable) {

        JPAQuery<ResponseStoryDto> query = jpaQueryFactory.select(new QResponseStoryDto(story, storyTag.content))
                .from(storyTag)
                .leftJoin(storyTag.story, story)
                .where(story.user.id.eq(userId))
                .where(storyTag.tag.id.eq(tagId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (tagId == 1) { // tagId가 날짜면 내림차순 정렬
            return query.orderBy(storyTag.content.desc())
                        .fetch();
        }else{ // tagId가 날짜가 아니면 오름차순 정렬
            return query.orderBy(storyTag.content.asc())
                    .fetch();
        }

    }

}
