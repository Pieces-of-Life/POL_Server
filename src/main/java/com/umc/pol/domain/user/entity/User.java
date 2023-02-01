package com.umc.pol.domain.user.entity;

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
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "nickname")
  private String nickname;

  @NotNull
  @Column(name = "score")
  private Long score;

  @NotNull
  @Column(name = "level")
  private Long level;

  @NotNull
  @Column(name = "profile_img")
  private String profile_img;

  @Builder
  public User (
    Long id,
    String nickname,
    Long score,
    Long level,
    String profile_img
  ){
    this.id = id;
    this.nickname = nickname;
    this.score = score;
    this.level = level;
    this.profile_img = profile_img;
  }

}
