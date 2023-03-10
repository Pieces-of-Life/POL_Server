package com.umc.pol.domain.story.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.umc.pol.domain.story.dto.QResponseStoryDto;
import com.umc.pol.domain.story.dto.ResponseStoryDto;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.pol.domain.story.entity.Story;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.provider.symmetric.CAST5;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.umc.pol.domain.story.entity.QStory.story;
import static com.umc.pol.domain.user.entity.QUser.user;
import static com.umc.pol.domain.story.entity.QStoryTag.storyTag;

import java.util.List;


@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Story> findStory(Pageable pageable, Long cursorId) {

    List<Story> content = queryFactory
      .selectFrom(story)
      .join(story.user, user).fetchJoin()
      .where(storyIdCursor(cursorId)
        .and(story.isOpen.eq(Boolean.TRUE)))
      .limit(pageable.getPageSize())
      .orderBy(storySort(pageable))
      .fetch();

    return new PageImpl<>(content);

  }

  @Override
  public Page<Story> findUserMainStory(Pageable pageable, Long cursorId, Long userId) {
    List<Story> content = queryFactory
      .selectFrom(story)
      .join(story.user, user).fetchJoin()
      .where(storyIdCursor(cursorId)
        .and(user.id.eq(userId))
        .and(story.isMain.eq(Boolean.TRUE)))
      .limit(pageable.getPageSize())
      .orderBy(storySort(pageable))
      .fetch();

    return new PageImpl<>(content);
  }

  private BooleanExpression storyIdCursor(Long cursorId){
    return cursorId == null ? null : story.id.gt(cursorId);
  }

  private OrderSpecifier<?> storySort(Pageable page){
    if (!page.getSort().isEmpty()) {
      for (Sort.Order order : page.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.DESC : Order.ASC;

        switch (order.getProperty()){
          case "recent":
            return new OrderSpecifier(direction, story.createdAt);
          case "like":
            return new OrderSpecifier(direction, story.likeCnt);
        }
      }
    }
    return new OrderSpecifier(Order.DESC, story.createdAt);
  }

    @Override
    public List<ResponseStoryDto> getFilterStoryPage(long userId, long tagId, Pageable pageable) {

        JPAQuery<ResponseStoryDto> query = queryFactory.select(new QResponseStoryDto(story, storyTag.content))
                .from(storyTag)
                .leftJoin(storyTag.story, story)
                .where(story.user.id.eq(userId))
                .where(storyTag.tag.id.eq(tagId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(storyFilterSort(pageable));

        return query.fetch();
    }

  private OrderSpecifier<?> storyFilterSort(Pageable page){
    if (!page.getSort().isEmpty()) {
      for (Sort.Order order : page.getSort()) {
        Order direction = order.getDirection().isDescending() ? Order.ASC : Order.DESC;

        switch (order.getProperty()){
          case "year":
          case "age":
            return new OrderSpecifier(direction, storyTag.content.castToNum(Integer.class));
        }
      }
    }
    return new OrderSpecifier(Order.ASC, storyTag.content);
  }

  @Override
  public Page<Story> findUserAllStory(Pageable pageable, Long userId){
    List<Story> content = queryFactory
            .selectFrom(story)
            .join(story.user, user).fetchJoin()
            .where(user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(story.createdAt.desc())
            .fetch();

    return new PageImpl<>(content);
  }

}
