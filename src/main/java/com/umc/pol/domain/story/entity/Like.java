package com.umc.pol.domain.story.entity;

import com.umc.pol.domain.user.entity.User;
import com.umc.pol.global.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "story_id")
  private Story story;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
