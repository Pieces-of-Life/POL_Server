package com.umc.pol.domain.story.repository;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.umc.pol.domain.story.entity.Story;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.umc.pol.domain.story.entity.QStory.story;
import static com.umc.pol.domain.user.entity.QUser.user;

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

  private BooleanExpression storyIdCursor(Long cursorId){
    return cursorId == null ? null : story.id.gt(cursorId);
  }

  private OrderSpecifier<?> storySort(Pageable page){
    if (!page.getSort().isEmpty()) {
      for (Sort.Order order : page.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.DESC : Order.ASC;

        switch (order.getProperty()){
          case "recent":
            return new OrderSpecifier(direction, story.updatedAt);
          case "like":
            return new OrderSpecifier(direction, story.likeCnt);
        }
      }
    }
    return new OrderSpecifier(Order.DESC, story.updatedAt);
  }

}
