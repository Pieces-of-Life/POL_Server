package com.umc.pol.domain.story.entity;

import com.umc.pol.domain.user.entity.User;
import com.umc.pol.global.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "like")
public class Like extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Like (
    Long id,
    Story story,
    User user
  ){
    this.id = id;
    this.story = story;
    this.user = user;
  }

}
