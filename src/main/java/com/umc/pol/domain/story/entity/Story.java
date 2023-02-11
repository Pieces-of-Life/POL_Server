package com.umc.pol.domain.story.entity;

import com.umc.pol.domain.user.entity.User;

import com.umc.pol.global.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "story")
public class Story extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
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

  @Column(name = "is_main", columnDefinition = "Boolean DEFAULT false")
  private Boolean isMain;

  @Column(name = "is_open", columnDefinition = "Boolean DEFAULT false")
  private Boolean isOpen;

  @Column(name = "like_cnt", columnDefinition = "Long DEFAULT 0")
  private Long likeCnt;

  @Builder
  public Story(
    Long id,
    User user,
    String title,
    String description,
    String color,
    Boolean isMain,
    Boolean isOpen,
    Long likeCnt
  ){
    this.id = id;
    this.user = user;
    this.title = title;
    this.description = description;
    this.color = color;
    this.isMain = isMain;
    this.isOpen = isOpen;
    this.likeCnt = likeCnt;
  }

  public void updateColor(String color){
    this.color = color;
  }

  public void changeIsOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }

  public void changeIsMain(boolean isMain) {
    this.isMain = isMain;
  }
}
