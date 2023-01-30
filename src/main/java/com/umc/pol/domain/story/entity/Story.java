package com.umc.pol.domain.story.entity;

import com.umc.pol.domain.user.entity.User;

import com.umc.pol.global.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "story")
public class Story extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull
  @Column(name = "title")
  private String title;

  @NotNull
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "color")
  private String color;

  @NotNull
  @Column(name = "is_main")
  @ColumnDefault("false")   // insert 문제 발생 시 @Column에서 정의하는 방식으로 변경될 수 있음
  private Boolean is_main;

  @NotNull
  @Column(name = "is_open")
  @ColumnDefault("false")   // insert 문제 발생 시 @Column에서 정의하는 방식으로 변경될 수 있음
  private Boolean is_open;

  @Builder
  public Story(
    Long id,
    User user,
    String title,
    String description,
    String color,
    Boolean is_main,
    Boolean is_open
  ){
    this.id = id;
    this.user = user;
    this.title = title;
    this.description = description;
    this.color = color;
    this.is_main = is_main;
    this.is_open = is_open;
  }

}
